package bg.paysafe.springboot.api.service.chat;

import bg.paysafe.springboot.api.entity.Conversation;
import bg.paysafe.springboot.api.entity.Message;
import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.exception.user.UserCannotDeleteMessageException;
import bg.paysafe.springboot.api.exception.user.UserNotFound;
import bg.paysafe.springboot.api.exception.user.UserNotParticipantInConversationException;
import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.chat.*;
import bg.paysafe.springboot.api.repository.ConversationRepository;
import bg.paysafe.springboot.api.repository.MessageRepository;
import bg.paysafe.springboot.api.repository.UserRepository;
import bg.paysafe.springboot.api.service.common.io.PictureService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static bg.paysafe.springboot.api.constant.ConfigurationConstants.PictureConfig.PATH_CHAT;
import static bg.paysafe.springboot.api.constant.Constants.*;
import static bg.paysafe.springboot.api.constant.Constants.Number.ONE;

@Service
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository conversationRepository;

    private final UserRepository userRepository;

    private final MessageRepository messageRepository;

    private final PictureService pictureService;


    public ChatServiceImpl(
            ConversationRepository conversationRepository,
            UserRepository userRepository,
            MessageRepository messageRepository,
            PictureService pictureService
    ) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.pictureService = pictureService;
    }

    @Override
    public FriendsMessagesViewModel getAllConversationsWithLastMessageByUserId(Long id, String sort, Integer page, Integer size, String direction) {
        final var pageable = PageRequest.of(page, size, Sort.by(
                direction.equals(DEFAULT_SORT) ?
                        Sort.Direction.ASC :
                        Sort.Direction.DESC,
                sort)
        );

        return FriendsMessagesViewModel.ofFriendsMessages(
                this.conversationRepository.findAllConversationsWithLastMessageByUserId(id, pageable)
        );
    }

    @Override
    public ConversationInfoViewModel createConversation(ConversationRequestModel model) {
        final var conversation = new Conversation();
        conversation.setStartedAt(model.getStartedAt());
        conversation.setUsers(this.userRepository.findAllById(model.getUsersId()));

        return ConversationInfoViewModel.ofConversation(
                this.conversationRepository.saveAndFlush(
                        conversation
                )
        );
    }

    @Override
    public ConversationViewModel findById(Long userId, Long conversationId, String sort, Integer page, Integer size) throws UserNotParticipantInConversationException {
        final var conversation = this.conversationRepository.findByIdAndUserId(userId, conversationId)
                .orElseThrow(UserNotParticipantInConversationException::new);

        final var messages = this.messageRepository.findAllByConversationId(
                conversationId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort))
        );

        return ConversationViewModel.ofConversation(conversation, messages);
    }

    @Override
    public MessageInfoViewModel createMessage(Long conversationId, MessageRequestModel model, Long userId) throws UserNotParticipantInConversationException {
        final var conversation = this.conversationRepository.findByIdAndUserId(userId, conversationId)
                .orElseThrow(UserNotParticipantInConversationException::new);

        final var message = this.setMessage(conversation, model.getMessage(), this.userRepository.findById(userId).get());

        return MessageInfoViewModel.ofMessage(
                this.messageRepository.saveAndFlush(message)
        );
    }

    @Override
    public ConversationInfoViewModel createConversationName(Long conversationId, ConversationNameRequestModel model, Long userId) throws UserNotParticipantInConversationException {
        final var conversation = this.conversationRepository.findByIdAndUserId(userId, conversationId)
                .orElseThrow(UserNotParticipantInConversationException::new);
        conversation.setName(model.getName());

        return ConversationInfoViewModel.ofConversation(
                this.conversationRepository.saveAndFlush(conversation)
        );
    }

    @Override
    public List<MessageInfoViewModel> findAllUnreadMessageByUserId(Long id) {
        return this.conversationRepository.findAllUnreadMessagesByUserId(id).stream()
                .map(MessageInfoViewModel::ofMessage)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageInfoViewModel> changeMessageSeenStatus(Long userId, SeenMessagesRequestModel model) {
        return this.conversationRepository
                .findAllMessagesByIdAndUserId(model.getMessageIds(), userId).stream()
                .map(Message::see)
                .map(this.messageRepository::saveAndFlush)
                .map(MessageInfoViewModel::ofMessage)
                .collect(Collectors.toList());
    }

    @Override
    public SuccessfulRequestViewModel deleteMessageById(Long id, DeleteMessageRequestModel model) throws UserCannotDeleteMessageException {
        final var message = this.messageRepository.findByIdAndUserId(model.getId(), id)
                .orElseThrow(UserCannotDeleteMessageException::new);

        if (message.getId().equals(message.getConversation().getLastMessage().getId())) {
            message.getConversation().setPreviousLastMessage();
            this.conversationRepository.saveAndFlush(message.getConversation());
        }

        if (message.getMessage().contains(PATH_CHAT)) {
            var m = message.getMessage().split(IMAGES_SEPARATOR_IN_MESSAGE);

            this.pictureService.deleteImg(m[0], m[1]);
        }

        this.messageRepository.deleteById(message.getId());

        return new SuccessfulRequestViewModel(true);
    }

    @Override
    public void createBurnMeSupportChat(User user) {
        final var support = this.userRepository.findById(ONE).get();
        final var today = java.sql.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        var conversation = new Conversation();
        conversation.setName("support");
        conversation.addUser(user);
        conversation.addUser(support);
        conversation.setStartedAt(today);

        var message = new Message();
        message.setMessage("supprt");
        message.setSeen(false);
        message.setSender(support);
        message.setCreatedAt(today);

        conversation = this.conversationRepository.saveAndFlush(conversation);
        final var m = this.messageRepository.saveAndFlush(message);

        conversation.setLastMessage(m);
        conversation.addMessage(m);
    }

    @Override
    public ConversationInfoViewModel createConversationWithMessage(Long userId, ConversationMessageRequestModel model) throws UserNotFound {
        var friend = this.userRepository.findById(model.getFriendId()).orElseThrow(UserNotFound::new);
        final var user = this.userRepository.findById(userId).orElseThrow(UserNotFound::new);
        var conversation = this.conversationRepository.findByUsersId(userId, model.getFriendId());

        if (conversation == null) {
            conversation = new Conversation();
            conversation.addUser(user);
            conversation.addUser(friend);
            conversation.setStartedAt(java.sql.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            conversation = this.conversationRepository.saveAndFlush(conversation);
        }

        this.messageRepository.saveAndFlush(this.setMessage(conversation, model.getMessage(), user));

        return ConversationInfoViewModel.ofConversation(conversation);
    }

    private Message setMessage(Conversation conversation, String message, User user) {
        var m = new Message();

        m.setMessage(message);
        m.setCreatedAt(java.sql.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        m.setConversation(conversation);
        m.setSender(user);

        conversation.addMessage(m);
        conversation.setLastMessage(m);

        return m;
    }

}

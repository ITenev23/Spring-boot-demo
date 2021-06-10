package bg.paysafe.springboot.api.service.chat;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.exception.user.UserCannotDeleteMessageException;
import bg.paysafe.springboot.api.exception.user.UserNotFound;
import bg.paysafe.springboot.api.exception.user.UserNotParticipantInConversationException;
import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.chat.*;

import java.util.List;

public interface ChatService {

    FriendsMessagesViewModel getAllConversationsWithLastMessageByUserId(Long id, String sort, Integer page, Integer size, String direction);

    ConversationInfoViewModel createConversation(ConversationRequestModel model);

    ConversationViewModel findById(Long userId, Long conversationId, String sort, Integer page, Integer size) throws UserNotParticipantInConversationException;

    MessageInfoViewModel createMessage(Long conversationId, MessageRequestModel model, Long userId) throws UserNotParticipantInConversationException;

    ConversationInfoViewModel createConversationName(Long id, ConversationNameRequestModel model, Long userId) throws UserNotParticipantInConversationException;

    List<MessageInfoViewModel> findAllUnreadMessageByUserId(Long id);

    List<MessageInfoViewModel> changeMessageSeenStatus(Long userId, SeenMessagesRequestModel model);

    SuccessfulRequestViewModel deleteMessageById(Long id, DeleteMessageRequestModel model) throws UserCannotDeleteMessageException;

    void createBurnMeSupportChat(User user);

    ConversationInfoViewModel createConversationWithMessage(Long userId, ConversationMessageRequestModel model) throws UserNotFound;

}

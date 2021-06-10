package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.exception.user.UserCannotDeleteMessageException;
import bg.paysafe.springboot.api.exception.user.UserNotFound;
import bg.paysafe.springboot.api.exception.user.UserNotParticipantInConversationException;
import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.chat.*;
import bg.paysafe.springboot.api.service.chat.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static bg.paysafe.springboot.api.constant.Constants.*;
import static bg.paysafe.springboot.api.constant.URLMappings.*;

@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(value = CONVERSATION_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FriendsMessagesViewModel> getAllConversationWithLastMessageByUserId(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_PROPERTIES_DATE) String sort,
            @RequestParam(value = "direction", required = false, defaultValue = DEFAULT_SORT) String direction,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) Integer size
    ) {
        return new ResponseEntity<>(this.chatService.getAllConversationsWithLastMessageByUserId(user.getId(), sort, page, size, direction), HttpStatus.OK);
    }

    @PostMapping(value = CONVERSATION_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ConversationInfoViewModel> createConversation(@RequestBody @Valid ConversationRequestModel model) {
        return new ResponseEntity<>(this.chatService.createConversation(model), HttpStatus.CREATED);
    }

    @PostMapping(value = CONVERSATION_BASE + MESSAGE_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ConversationInfoViewModel> createConversationWithMessage(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid ConversationMessageRequestModel model) throws UserNotFound {
        return new ResponseEntity<>(this.chatService.createConversationWithMessage(user.getId(),model), HttpStatus.CREATED);
    }

    @GetMapping(value = MESSAGE_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageInfoViewModel>> findAllUnreadMessageByUserId(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(this.chatService.findAllUnreadMessageByUserId(user.getId()), HttpStatus.OK);
    }

    @PostMapping(value = MESSAGE_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageInfoViewModel>> changeMessageSeenStatus(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid SeenMessagesRequestModel model
    ) {
        return new ResponseEntity<>(this.chatService.changeMessageSeenStatus(user.getId(), model), HttpStatus.CREATED);
    }

    @PostMapping(value = MESSAGE_DELETE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessfulRequestViewModel> deleteMessageById(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid DeleteMessageRequestModel model
    ) throws UserCannotDeleteMessageException {
        return new ResponseEntity<>(this.chatService.deleteMessageById(user.getId(), model), HttpStatus.OK);
    }

    @GetMapping(value = CONVERSATION_BY_ID)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ConversationViewModel> findById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_PROPERTIES_MESSAGE_DATE) String sort,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) Integer size
    ) throws UserNotParticipantInConversationException {
        return new ResponseEntity<>(this.chatService.findById(user.getId(), id, sort, page, size), HttpStatus.OK);
    }

    @PostMapping(value = CONVERSATION_BY_ID)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageInfoViewModel> createMessage(
            @PathVariable Long id,
            @RequestBody @Valid MessageRequestModel model,
            @AuthenticationPrincipal User user
    ) throws UserNotParticipantInConversationException {
        return new ResponseEntity<>(this.chatService.createMessage(id, model, user.getId()), HttpStatus.CREATED);
    }

    @PostMapping(value = CREATE_CONVERSATION_NAME)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ConversationInfoViewModel> createConversationName(
            @PathVariable Long id,
            @RequestBody @Valid ConversationNameRequestModel model,
            @AuthenticationPrincipal User user
    ) throws UserNotParticipantInConversationException {
        return new ResponseEntity<>(this.chatService.createConversationName(id, model, user.getId()), HttpStatus.CREATED);
    }


}

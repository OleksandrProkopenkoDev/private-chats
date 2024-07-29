package ua.spro.privatechat.chat;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

  private final ChatMessageService chatMessageService;
  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/chat")
  public void processMessage(@Payload ChatMessage chatMessage) {
    ChatMessage savedChatMessage = chatMessageService.save(chatMessage);
    // john/queue/messages
    messagingTemplate.convertAndSendToUser(
        chatMessage.getRecipientId(),
        "/queue/messages",
        ChatNotification.builder()
            .id(savedChatMessage.getId())
            .senderId(savedChatMessage.getSenderId())
            .recipientId(savedChatMessage.getRecipientId())
            .content(savedChatMessage.getContent())
            .build());
  }

  @GetMapping("/messages/{senderId}/{recipientId}")
  public ResponseEntity<List<ChatMessage>> findChatMessages(
      @PathVariable("senderId") String senderId, @PathVariable("recipientId") String recipientId) {
    return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
  }
}

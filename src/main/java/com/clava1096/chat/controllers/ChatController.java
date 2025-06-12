package com.clava1096.chat.controllers;

import com.clava1096.chat.exceptions.UserNotFoundException;
import com.clava1096.chat.models.ChatNotification;
import com.clava1096.chat.models.Message;
import com.clava1096.chat.models.User;
import com.clava1096.chat.models.dto.chat.MessageDTO;
import com.clava1096.chat.services.ChatRoomService;
import com.clava1096.chat.services.MessageService;
import com.clava1096.chat.services.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {
    final private ChatRoomService chatRoomService;

    final private MessageService messageService;

    final private SimpMessagingTemplate messagingTemplate;

    final private UserService userService;

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageDTO messageDTO) {
        User sender = userService.findById(messageDTO.getSenderId());
        User receiver = userService.findById(messageDTO.getReceiverId());
        if (sender == null || receiver == null) {
            throw new UserNotFoundException("Sender or Receiver not found");
        }
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setText(messageDTO.getContent());
        var chat = chatRoomService.getChatIdByParticipants(sender.getId(), receiver.getId(), true);
        message.getChatRoom().setId(chat.get());

        Message saved = messageService.save(message);

        // Отправляем уведомление с минимумом данных
        messagingTemplate.convertAndSendToUser(
                receiver.getUsername(), "/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSender().getId(),
                        saved.getSender().getUsername()
                )
        );
    }

    @GetMapping("/messages/{senderId}/{receiverId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable Long senderId,
            @PathVariable Long receiverId
            ) {
        return ResponseEntity
                .ok(messageService.countNewMessages(senderId, receiverId));
    }

    

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages (@PathVariable Long senderId,
                                               @PathVariable Long recipientId) {
        return ResponseEntity
                .ok(messageService.getChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage (@PathVariable UUID id) {
        return ResponseEntity
                .ok(messageService.findById(id));
    }

}

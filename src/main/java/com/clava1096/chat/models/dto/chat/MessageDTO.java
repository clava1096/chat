package com.clava1096.chat.models.dto.chat;

import com.clava1096.chat.models.enumpack.MessageStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {
    private UUID id;
    private String content;
    private Long senderId;
    private Long receiverId;
    private UUID chatRoomId;
    private MessageStatus messageStatus;
}

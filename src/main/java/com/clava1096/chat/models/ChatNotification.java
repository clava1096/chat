package com.clava1096.chat.models;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {
    private UUID id;
    private Long senderId;
    private String senderName;
}

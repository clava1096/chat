package com.clava1096.chat.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id", nullable = false)
    private UUID id;

    @Column(name = "text")
    private String text;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sender_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "fk_message_sender_id"),
            nullable = false
    )
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "receiver_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "fk_message_receiver_id"),
            nullable = false
    )
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "chat_room_id",
            referencedColumnName = "chat_room_id",
            foreignKey = @ForeignKey(name = "fk_chat_room_id")
    )
    private ChatRoom chatRoom;
}

package com.clava1096.chat.models.repositories;

import com.clava1096.chat.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    Optional<ChatRoom> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
}

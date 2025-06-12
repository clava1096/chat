package com.clava1096.chat.models.repositories;

import com.clava1096.chat.models.Message;
import com.clava1096.chat.models.enumpack.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    long countBySenderIdAndReceiverIdAndMessageStatus(Long senderId, Long receiverId, MessageStatus messageStatus);

    @Query("SELECT m FROM Message m " +
            "WHERE (m.sender.id = :user1Id AND m.receiver.id = :user2Id) " +
            "OR (m.sender.id = :user2Id AND m.receiver.id = :user1Id)")
    List<Message> findChatMessages(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);

    @Modifying
    @Query("UPDATE Message m SET m.messageStatus = :status " +
            "WHERE m.sender.id = :senderId AND m.receiver.id = :receiverId " +
            "AND m.messageStatus = :currentStatus")
    int updateStatuses(
            @Param("senderId") Long senderId,
            @Param("receiverId") Long receiverId,
            @Param("status") MessageStatus status,
            @Param("currentStatus") MessageStatus currentStatus
    ); //package update status
}

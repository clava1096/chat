package com.clava1096.chat.services;

import com.clava1096.chat.exceptions.ResourceNotFoundException;
import com.clava1096.chat.models.Message;
import com.clava1096.chat.models.enumpack.MessageStatus;
import com.clava1096.chat.models.repositories.MessageRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;


    @Transactional
    public Message save(Message message) {
        message.setMessageStatus(MessageStatus.RECEIVED);
        messageRepository.save(message);
        return message;
    }


    @Transactional(readOnly = true)
    public List<Message> getChatMessages(Long senderId, Long receiverId) {
        updateStatuses(senderId, receiverId);
        return messageRepository.findChatMessages(senderId, receiverId);
    }

    @Transactional(readOnly = true)
    public long countNewMessages(Long senderId, Long receiverId) {
        return messageRepository.countBySenderIdAndReceiverIdAndMessageStatus(
                senderId, receiverId, MessageStatus.RECEIVED
        );
    }

    private void updateStatuses(Long senderId, Long receiverId) {
        messageRepository.updateStatuses(
                senderId,
                receiverId,
                MessageStatus.DELIVERED,
                MessageStatus.RECEIVED
        );
    }

    public Message findById(UUID id) {
        return messageRepository
                .findById(id)
                .map(message -> {
                    message.setMessageStatus(MessageStatus.DELIVERED);
                    return messageRepository.save(message);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("Сообщение не найдено (" + id + ")"));
    }
}
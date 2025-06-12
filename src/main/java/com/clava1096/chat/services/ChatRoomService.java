package com.clava1096.chat.services;

import com.clava1096.chat.models.ChatRoom;
import com.clava1096.chat.models.repositories.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    final ChatRoomRepository chatRoomRepository;

    public Optional<UUID> getChatIdByParticipants(Long senderId, Long recipientId, boolean createIfNotExist) {
        // 1. Пытаемся найти существующий чат в одном направлении
        Optional<ChatRoom> existingChat = chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId);

        // 2. Если не нашли, проверяем обратное направление
        if (existingChat.isEmpty()) {
            existingChat = chatRoomRepository
                    .findBySenderIdAndRecipientId(recipientId, senderId);
        }

        // 3. Если чат найден (в любом направлении), возвращаем его ID
        if (existingChat.isPresent()) {
            return existingChat.map(ChatRoom::getId);
        }

        // 4. Если не нужно создавать новый чат
        if (!createIfNotExist) {
            return Optional.empty();
        }

        // 5. Создаем новую запись чата
        UUID chatId = UUID.randomUUID();
        ChatRoom newChat = ChatRoom.builder()
                .id(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        chatRoomRepository.save(newChat);

        return Optional.of(chatId);
    }
}

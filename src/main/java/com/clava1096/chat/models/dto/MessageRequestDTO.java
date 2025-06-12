package com.clava1096.chat.models.dto;

import com.clava1096.chat.models.enumpack.MessageStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Schema(description = "Сообщение")
@Getter
@AllArgsConstructor
public class
MessageRequestDTO {
    @Schema(description = "текст сообщения", example = "Привет")
    @JsonProperty(value = "text", defaultValue = "Привет", required = true)
    private String text;

    @Schema(description = "id отправителя", example = "")
    @JsonProperty(value = "senderId", defaultValue = "", required = true)
    @NotNull(message = "ID отправителя обязательно")
    UUID senderId;

    @Schema(description = "id получателя", example = "")
    @JsonProperty(value = "senderId", defaultValue = "", required = true)
    @NotNull(message = "ID получателя обязательно")
    UUID receiverId;

    @Schema(description = "id чат комнаты", example = "")
    @JsonProperty(value = "senderId", defaultValue = "", required = true)
    UUID chatRoomId;

    @Schema(description = "статус сообщения", example = "")
    @JsonProperty(value = "senderId", defaultValue = "", required = true)
    MessageStatus messageStatus;
}

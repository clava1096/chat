package com.clava1096.chat.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class MessageDTO {
    @Schema(description = "Идентификатор сообщения", example = "8e262c04-a090-11e8-98d0-529269fb1459")
    @JsonProperty(value = "id", defaultValue = "8e262c04-a090-11e8-98d0-529269fb1459", required = true)
    private UUID id;

    @Schema(description = "Текст сообщения", example = "Привет")
    @JsonProperty(value = "text", defaultValue = "Привет", required = true)
    private String text;
    ///
    /// 
}

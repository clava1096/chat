package com.clava1096.chat.models.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    private String message;
    private List<String> details;
}

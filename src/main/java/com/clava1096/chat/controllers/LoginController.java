package com.clava1096.chat.controllers;

import com.clava1096.chat.config.jwt.JwtTokenService;
import com.clava1096.chat.models.dto.security.LoginRequest;
import com.clava1096.chat.models.dto.security.LoginResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "registration-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class LoginController {
    private final JwtTokenService jwtTokenService;

    @PostMapping("api/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("login request: {}", loginRequest);
        final LoginResponse loginResponse = jwtTokenService.getLoginResponse(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}

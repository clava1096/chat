package com.clava1096.chat.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/oauth")
    public Map<String, Object> oauth(
            @AuthenticationPrincipal OAuth2User user
            ) {
        return user.getAttributes();
    }
}

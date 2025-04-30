package com.clava1096.chat.services;

import com.clava1096.chat.mappers.UserMapper;
import com.clava1096.chat.models.User;
import com.clava1096.chat.models.enumpack.UserRole;
import com.clava1096.chat.models.repositories.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;


    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        userRepository.findByEmail(oAuth2User.getAttribute("email"))
                .or(() -> Optional.of(
                        User.builder()
                                .email(oAuth2User.getAttribute("email"))
                                .username(oAuth2User.getAttribute("name"))
                                .pictureUrl(oAuth2User.getAttribute("picture"))
                                .emailVerified(oAuth2User.getAttribute("email_verified"))
                                .providerId(oAuth2User.getAttribute("sub"))
                                .userRole(UserRole.ROLE_USER)
                                .build()
                ))
                .ifPresent(userRepository::save);
        return oAuth2User;
    }
}

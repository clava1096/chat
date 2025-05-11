package com.clava1096.chat.services.security;

import com.clava1096.chat.exceptions.ConflictException;
import com.clava1096.chat.models.dto.security.RegistrationRequest;
import com.clava1096.chat.models.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private static final String EMAIL_ALREADY_EXISTS = "Email already exists ";

    private static final String USERNAME_ALREADY_EXISTS = "Username already exists ";

    private final UserRepository userRepository;

    public void validateUser(RegistrationRequest registrationRequest ) {
        final String email = registrationRequest.getEmail();
        final String username = registrationRequest.getUsername();

        checkEmail(email);
        checkUsername(username);
    }


    private void checkEmail(String email) {
        final boolean existsUser = userRepository.existsByEmail(email);
        final boolean isInvalidEmail = !EmailValidator.getInstance().isValid(email);
        Objects.requireNonNull(email, "Email cannot be null");
        if (isInvalidEmail) {
            throw new IllegalArgumentException("Email is invalid: " + email);
        }

        if (existsUser) {
            throw new ConflictException(EMAIL_ALREADY_EXISTS + email);
        }
    }

    private void checkUsername(String username) {
        final boolean exitsUser = userRepository.existsByUsername(username);
        if(exitsUser) {
            throw new ConflictException(USERNAME_ALREADY_EXISTS + username);
        }
    }
}

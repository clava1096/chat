package com.clava1096.chat.services.security;

import com.clava1096.chat.mappers.UserMapper;
import com.clava1096.chat.models.User;
import com.clava1096.chat.models.dto.security.AuthenticatedUserDTO;
import com.clava1096.chat.models.dto.security.RegistrationRequest;
import com.clava1096.chat.models.dto.security.RegistrationResponse;
import com.clava1096.chat.models.enumpack.UserRole;
import com.clava1096.chat.models.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

    private final UserRepository userRepository;

    private final UserValidationService userValidationService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public RegistrationResponse registration(RegistrationRequest registrationRequest) {
        userValidationService.validateUser(registrationRequest);
        final User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registrationRequest.getPassword()));
        user.setEmail(registrationRequest.getEmail());
        user.setUserRole(UserRole.ROLE_USER);
        userRepository.save(user);
        return new RegistrationResponse(REGISTRATION_SUCCESSFUL);
    }

    @Override
    public AuthenticatedUserDTO findAuthenticatedUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("User not authenticated");
        }
        String username;
        if (authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        } else if (authentication.getPrincipal() instanceof OAuth2User) {
            username = ((OAuth2User) authentication.getPrincipal()).getAttribute("username");
        } else {
            throw new IllegalStateException("Unsupported authentication type");
        }
        return userRepository.findByUsername(username);
    }
}

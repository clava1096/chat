package com.clava1096.chat.config.jwt;

import com.clava1096.chat.mappers.UserMapper;
import com.clava1096.chat.models.User;
import com.clava1096.chat.models.dto.security.AuthenticatedUserDTO;
import com.clava1096.chat.models.dto.security.LoginRequest;
import com.clava1096.chat.models.dto.security.LoginResponse;
import com.clava1096.chat.services.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService {
    private final UserService userService;

    private final JwtTokenManager jwtTokenManager;

    private final AuthenticationManager authenticationManager;

    public LoginResponse getLoginResponse(LoginRequest loginRequest) {
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();
        log.info("getLoginResponse username: {}, password: {}", username, password);
        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        log.info("usernamePasswordAuthenticationToken: {}", usernamePasswordAuthenticationToken);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        final AuthenticatedUserDTO authenticatedUserDto = userService.findAuthenticatedUserByUsername(username);
        log.info(authenticatedUserDto.getName());
        final User user = UserMapper.INSTANCE.convertToUser(authenticatedUserDto);
        final String token = jwtTokenManager.generateToken(user);

        return new LoginResponse(token);
    }
}

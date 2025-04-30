package com.clava1096.chat.services.security;

import com.clava1096.chat.models.User;
import com.clava1096.chat.models.dto.security.AuthenticatedUserDTO;
import com.clava1096.chat.models.dto.security.RegistrationRequest;
import com.clava1096.chat.models.dto.security.RegistrationResponse;

public interface UserService {
    User findByUsername(String username);

    RegistrationResponse registration(RegistrationRequest registrationRequest);

    AuthenticatedUserDTO findAuthenticatedUserByUsername(String username);

}

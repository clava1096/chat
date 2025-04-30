package com.clava1096.chat.mappers;

import com.clava1096.chat.models.User;
import com.clava1096.chat.models.dto.security.RegistrationRequest;
import org.mapstruct.factory.Mappers;

public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(RegistrationRequest registrationRequest);

    AuthenticatedUserDto convertToAuthenticatedUserDto(User user);

    User convertToUser(AuthenticatedUserDto authenticatedUserDto);

    OAuth2UserDTO convertToOAuth2UserDto(User user);
}

package com.clava1096.chat.mappers;

import com.clava1096.chat.models.User;
import com.clava1096.chat.models.dto.security.AuthenticatedUserDTO;
import com.clava1096.chat.models.dto.security.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(RegistrationRequest registrationRequest);

    AuthenticatedUserDTO convertToAuthenticatedUserDto(User user);

    User convertToUser(AuthenticatedUserDTO authenticatedUserDto);

//    OAuth2UserDTO convertToOAuth2UserDto(User user);
}

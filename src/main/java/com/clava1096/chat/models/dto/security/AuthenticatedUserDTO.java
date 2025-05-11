package com.clava1096.chat.models.dto.security;

import com.clava1096.chat.models.enumpack.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedUserDTO {

    private String name;

    private String username;

    private String password;

    private UserRole userRole;


}

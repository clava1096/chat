package com.clava1096.chat.models;

import com.clava1096.chat.models.enumpack.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password; // null for oauth

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "user_role")
    private UserRole userRole = UserRole.ROLE_USER;

    private String provider; // google

    @Column(name = "provider_id")
    private String providerId;

    private String pictureUrl;  // URL аватарки из Google, иначе загрузить самому

    @Column(name = "email_verified")
    private boolean emailVerified = false;  // true/false

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

}

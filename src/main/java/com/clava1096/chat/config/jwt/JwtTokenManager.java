package com.clava1096.chat.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.clava1096.chat.models.User;
import com.clava1096.chat.models.enumpack.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenManager {
    private final JwtProperties jwtProperties;

    public String generateToken(User user) {
        final String username = user.getUsername();
        final UserRole userRole = user.getUserRole();
        return JWT.create()
                .withSubject(username)
                .withIssuer(jwtProperties.getIssuer())
                .withClaim("role", userRole.getAuthority())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpiration() * 60 * 1000))
                .sign(Algorithm.HMAC256(jwtProperties.getSecret().getBytes()));
    }
    public boolean validateToken(String token, String authenticatedUsername) {

        final String usernameFromToken = getUsernameFromToken(token);

        final boolean equalsUsername = usernameFromToken.equals(authenticatedUsername);
        final boolean tokenExpired = isTokenExpired(token);

        return equalsUsername && !tokenExpired;
    }
    public String getUsernameFromToken(String token) {

        final DecodedJWT decodedJWT = getDecodedJWT(token);

        return decodedJWT.getSubject();

    }

    private boolean isTokenExpired(String token) {

        final Date expirationDateFromToken = getExpirationDateFromToken(token);
        return expirationDateFromToken.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {

        final DecodedJWT decodedJWT = getDecodedJWT(token);

        return decodedJWT.getExpiresAt();
    }

    private DecodedJWT getDecodedJWT(String token) {

        final JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecret().getBytes())).build();

        return jwtVerifier.verify(token);
    }

}

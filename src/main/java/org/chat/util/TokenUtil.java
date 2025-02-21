package org.chat.util;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import lombok.Data;
import org.chat.dto.AuthUser;

@Data
public class TokenUtil {
    public static String generateToken(AuthUser authUser) {
        Set<String> roles = new HashSet<>();
        roles.add("user");
        return Jwt.issuer("chat-quarkus")
                .subject(String.valueOf(authUser.getId()))
                .claim("username", authUser.getUsername())
                .groups(roles)
                .expiresAt(Instant.now().plus(Duration.ofHours(1)))
                .sign();
    }
}

package org.chat.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.chat.dto.AuthUser;
import org.chat.dto.LoginRequest;
import org.chat.entity.User;
import org.chat.util.TokenUtil;

import java.util.Collections;
import java.util.Map;

import static org.chat.common.ErrorCode.BAD_CREDENTIALS;
import static org.chat.exception.BaseException.throwIf;

@ApplicationScoped
public class AuthenticationService {

    @Transactional
    public Map<String, String> auth(LoginRequest request) {
        User existingUser = User.find("username", request.getUsername()).firstResult();
        throwIf(existingUser == null || !existingUser.getPassword().equals(request.getPassword()),
                BAD_CREDENTIALS, request.toString());
        String jwt = TokenUtil.generateToken(
                AuthUser.builder()
                        .id(existingUser.id)
                        .username(existingUser.getUsername()).build());
        return Collections.singletonMap("token", jwt);
    }
}

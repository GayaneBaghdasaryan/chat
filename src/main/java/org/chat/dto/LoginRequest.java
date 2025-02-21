package org.chat.dto;

import io.smallrye.common.constraint.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}


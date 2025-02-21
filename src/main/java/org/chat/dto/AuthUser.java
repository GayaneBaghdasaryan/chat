package org.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUser {
    private Long id;
    private String username;
    private String email;
}

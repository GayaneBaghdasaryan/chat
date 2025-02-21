package org.chat.common;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_PARAM("error.not.exist", Response.Status.BAD_REQUEST),

    TOKEN_INVALID("error.request.token.invalid", Response.Status.NOT_ACCEPTABLE),
    TOKEN_EXPIRED("error.request.token.expired", Response.Status.NOT_ACCEPTABLE),

    BAD_CREDENTIALS("error.user.bad.credentials", Response.Status.BAD_REQUEST),
    USER_NOT_FOUND( "error.user.not.found", Response.Status.BAD_REQUEST),
    GROUP_NOT_FOUND( "error.group.not.found", Response.Status.NOT_FOUND);

    private final String messageKey;
    private final Response.Status httpStatus;
}

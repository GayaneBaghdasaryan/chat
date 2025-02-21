package org.chat.exception;

import io.quarkus.arc.ArcUndeclaredThrowableException;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.chat.common.MessageBundle;
import org.chat.common.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {
    @Inject
    MessageBundle messageBundle;

    @Override
    public Response toResponse(Exception throwable) {
        var errorResponse = ErrorResponse.builder()
                .status(false);

        if (throwable instanceof BaseException exception) {
            var message = messageBundle.getMessage(exception.errorCode.getMessageKey(), exception.identifier);
            return Response.status(exception.errorCode.getHttpStatus()).
                    entity((errorResponse
                            .messageDetails(message)
                            .build())).build();
        }  else if (throwable instanceof ConstraintViolationException) {
            String errorMessage = throwable.getCause().getCause().getMessage();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse.messageDetails(errorMessage).build())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity((errorResponse
                            .messageDetails(throwable.getMessage())
                            .build()))
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }
}
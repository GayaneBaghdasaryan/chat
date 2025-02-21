package org.chat.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.chat.dto.LoginRequest;
import org.chat.service.AuthenticationService;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class AuthenticationResource {

    @Inject
    AuthenticationService authenticationService;

    @POST
    public Response login(@Valid LoginRequest request) {
        log.info("Login user: {}", request);
        return Response.ok(authenticationService.auth(request)).build();
    }


}

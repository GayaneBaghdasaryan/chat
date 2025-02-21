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
import org.chat.dto.UserDto;
import org.chat.service.UserService;

@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class UserResource {
    @Inject
    UserService userService;

    @POST
    public Response register(@Valid UserDto userDto) {
        log.info("Register user:{} ", userDto.getUsername());
        return Response.ok(userService.register(userDto)).build();
    }

}
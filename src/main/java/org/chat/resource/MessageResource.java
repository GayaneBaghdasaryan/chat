package org.chat.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.chat.dto.MessageRequest;
import org.chat.service.MessageHistoryService;
import org.chat.service.MessageService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@SecurityScheme(
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
)
@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

    @Inject
    MessageService messageService;
    @Inject
    MessageHistoryService historyService;
    @Inject
    JsonWebToken jwt;

    @POST
    @RolesAllowed("user")
    public Response sendMessage(@Valid MessageRequest request) {
        var sender = jwt.getClaim("username");
        messageService.sendMessage(request, sender.toString());
        return Response.ok("Message sent successfully").build();
    }

    @GET
    @Path("/sent")
    @RolesAllowed("user")
    public Response getSentHistory() {
        var sender = jwt.getClaim("username");
        return Response.ok(historyService.getHistoryBySender(sender.toString())).build();
    }

    @GET
    @Path("/received")
    @RolesAllowed("user")
    public Response getReceivedHistory() {
        var received = jwt.getClaim("username");
        return Response.ok(historyService.getHistoryByRecipient(received.toString())).build();
    }
}

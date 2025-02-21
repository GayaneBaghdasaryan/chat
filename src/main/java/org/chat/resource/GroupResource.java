package org.chat.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.chat.dto.GroupDto;
import org.chat.service.GroupService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@SecurityScheme(
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
)
@Path("/group")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class GroupResource {

    @Inject
    GroupService groupService;

    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/create")
    @RolesAllowed("user")
    public Response createGroup(@Valid GroupDto groupDto){
        var admin = jwt.getName();
        log.info("Creating message group:{}", groupDto);
        return Response.ok(groupService.create(groupDto, Long.valueOf(admin))).build();
    }

    @POST
    @Path("/search")
    @RolesAllowed("user")
    public Response searchGroup(@QueryParam("groupName") @NotBlank String groupName){
        var username = jwt.getName();
        log.info("Searching message group:{}", groupName);
        return Response.ok(groupService.search(groupName, Long.valueOf(username))).build();
    }
}

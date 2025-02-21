package org.chat.security;

import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.SneakyThrows;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenFilter implements ContainerRequestFilter {
    @ConfigProperty(name = "security.filter.bypass.paths")
    String bypassPaths;

    @SneakyThrows
    @Override
    public void filter(ContainerRequestContext requestContext){
        String path = requestContext.getUriInfo().getPath();

        for (String bypass : bypassPaths.split(",")) {
            if (path.trim().equals(bypass.trim())) {
                return;
            }
        }
        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abortWithUnauthorized(requestContext, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        JWTClaimsSet jwt = JwtUtil.parseAndValidateToken(token);

        if (jwt == null || jwt.getClaims().isEmpty()) {
            abortWithUnauthorized(requestContext, "Invalid or missing token");
        }

    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity(message)
                .build());
    }

}

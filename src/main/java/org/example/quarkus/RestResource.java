package org.example.quarkus;

import io.quarkus.security.identity.SecurityIdentity;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/rest")
@Produces({"application/json"})
@ApplicationScoped
public class RestResource {

    public static final String EXTRA = "Extra";
    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Produces({"application/json"})
    @RolesAllowed(EXTRA)
    public Response get() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", securityIdentity.getPrincipal().getName());
        response.put("roles", securityIdentity.getRoles());
        return Response.ok(response).build();
    }
}
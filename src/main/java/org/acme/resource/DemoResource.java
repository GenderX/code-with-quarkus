package org.acme.resource;

import org.acme.entity.Demo;
import org.acme.interceptor.Logged;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;
import java.util.List;

@Path("/demo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Logged
@Blocking
public class DemoResource {

    @GET
    public Uni<List<Demo>> getAllDemos() {
        return Uni.createFrom().item(() -> Demo.listAll());
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getDemoById(@PathParam("id") Long id) {
        return Uni.createFrom().item(() -> {
            Demo demo = Demo.findById(id);
            if (demo != null) {
                return Response.ok(demo).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        });
    }

    @POST
    @Transactional
    public Uni<Response> createDemo(Demo demo) {
        return Uni.createFrom().item(() -> {
            demo.persist();
            return Response.status(Response.Status.CREATED).entity(demo).build();
        });
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Uni<Response> updateDemo(@PathParam("id") Long id, Demo updatedDemo) {
        return Uni.createFrom().item(() -> {
            Demo demo = Demo.findById(id);
            if (demo != null) {
                demo.name = updatedDemo.name;
                demo.description = updatedDemo.description;
                demo.persist();
                return Response.ok(demo).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        });
    }

    @PUT
    @Path("/{id}/no-result-log")
    @Logged(logParameters = false, logResult = true, logExecutionTime = true)
    @Transactional
    public Uni<Response> updateDemoNoResultLog(@PathParam("id") Long id, Demo demo) {
        return updateDemo(id, demo);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Uni<Response> deleteDemo(@PathParam("id") Long id) {
        return Uni.createFrom().item(() -> {
            Demo demo = Demo.findById(id);
            if (demo != null) {
                demo.delete();
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        });
    }
}
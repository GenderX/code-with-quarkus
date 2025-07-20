package org.acme.resource;

import org.acme.entity.Demo;
import org.acme.service.DemoService;
import org.acme.interceptor.Logged;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/demo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Logged
public class DemoResource {

    @Inject
    DemoService demoService;

    @GET
    public List<Demo> getAllDemos() {
        return demoService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getDemoById(@PathParam("id") Long id) {
        Demo demo = demoService.findById(id);
        if (demo != null) {
            return Response.ok(demo).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response createDemo(Demo demo) {
        Demo created = demoService.create(demo);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateDemo(@PathParam("id") Long id, Demo demo) {
        Demo updated = demoService.update(id, demo);
        if (updated != null) {
            return Response.ok(updated).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}/no-result-log")
    @Logged(logParameters = false, logResult = true, logExecutionTime = true)
    public Response updateDemoNoResultLog(@PathParam("id") Long id, Demo demo) {
        Demo updated = demoService.updateWithoutResultLogging(id, demo);
        if (updated != null) {
            return Response.ok(updated).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDemo(@PathParam("id") Long id) {
        boolean deleted = demoService.delete(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
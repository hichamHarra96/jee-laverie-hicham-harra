package org.eclipse.jakarta.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.jakarta.dao.MachineDao;
import org.eclipse.jakarta.model.Machine;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.ws.rs.HeaderParam;


import java.security.Key;

import java.util.List;


@Path("/machines")
public class MachineResource {
    private Key secretKey = TokenUtils.getSecretKey();
    @Inject
    MachineDao machineDao;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMachines(@HeaderParam("Authorization") String authorizationHeader) {
        if (!validateToken(authorizationHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        List<Machine> machines = machineDao.getAllMachines();
        return Response.ok(machines, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMachine(@HeaderParam("Authorization") String authorizationHeader, Machine machine) {
        if (!validateToken(authorizationHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        machineDao.addMachine(machine);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMachine(@HeaderParam("Authorization") String authorizationHeader, @PathParam("id") int id) {
        if (!validateToken(authorizationHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        Machine machine = machineDao.getMachineById(id);
        if (machine == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Machine not found").build();
        }

        return Response.ok(machine, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateMachine(@HeaderParam("Authorization") String authorizationHeader, @PathParam("id") int id, Machine machine) {
        if (!validateToken(authorizationHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        Machine existingMachine = machineDao.getMachineById(id);
        if (existingMachine == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Machine not found").build();
        }

        existingMachine.setStatus(machine.getStatus());

        machineDao.updateMachine(existingMachine);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMachine(@HeaderParam("Authorization") String authorizationHeader, @PathParam("id") int id) {
        if (!validateToken(authorizationHeader)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        Machine machine = machineDao.getMachineById(id);
        if (machine == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Machine not found").build();
        }

        machineDao.deleteMachine(machine);
        return Response.ok().build();
    }


    private boolean validateToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return false;
        }

        String token = authorizationHeader.substring(7);
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

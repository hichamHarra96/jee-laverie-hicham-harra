package org.eclipse.jakarta.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.jakarta.dao.UserDao;
import org.eclipse.jakarta.model.User;
import io.jsonwebtoken.Jwts;
import jakarta.ws.rs.HeaderParam;


import java.security.Key;
import java.util.Date;
import java.util.List;


@Path("/auth")
public class AuthResource {
    private Key secretKey = TokenUtils.getSecretKey();
    @Inject
    UserDao userDao;

    @GET
    @Path("/users")
    public Response getUsers() {
        List<User> users = userDao.getAllUsers();
        return Response.ok(users, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        User existingUser = userDao.getUserByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            String token = generateToken(existingUser.getUsername());
            return Response.ok().header("Authorization", "Bearer " + token).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }
    }



    @POST
    @Path("/signup")
    @Transactional
    @Consumes
    public Response signup(User user) {
        userDao.addUser(user);
        return Response.status(Response.Status.CREATED).build();
    }
    @POST
    @Path("/logout")
    public Response logout(@HeaderParam("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        return Response.ok().entity("Logged out").build();
    }
    private String generateToken(String username) {
        Date expiration = new Date(System.currentTimeMillis() + 3600000);

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();

        return token;
    }
}

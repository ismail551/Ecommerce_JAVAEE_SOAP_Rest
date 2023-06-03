/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jee.dao;



import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.mycompany.jee.dao.clientDAO;
import com.mycompany.jee.entities.client;

@Path("/account")
public class AccountResource {
    private clientDAO cdao;

    public AccountResource() {
        cdao = new clientDAO();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(client credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();

        // Perform login authentication using the clientDAO
        client c = cdao.login(email, password);

        if (c != null) {
            // Return a success response
            return Response.ok(c).build();
        } else {
            // Return an error response
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(client c) {
        // Check if the client already exists
        if (cdao.login(c.getEmail(), c.getPassword()) != null) {
            // Return an error response
            return Response.status(Response.Status.CONFLICT).build();
        }

        // Register the client using the clientDAO
        client registeredClient = cdao.register(c);

        if (registeredClient != null) {
            // Return a success response
            return Response.ok(registeredClient).build();
        } else {
            // Return an error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

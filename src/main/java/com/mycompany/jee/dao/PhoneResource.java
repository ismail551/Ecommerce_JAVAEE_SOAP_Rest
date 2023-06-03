/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jee.dao;


import com.mycompany.jee.dao.phoneDAO;
import com.mycompany.jee.entities.phone;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/phones")
public class PhoneResource {

    private phoneDAO phoneDAO;

    public PhoneResource() {
        phoneDAO = new phoneDAO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhones() {
        ArrayList<phone> phones = phoneDAO.getPhones();
        return Response.ok(phones).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhoneById(@PathParam("id") int id) {
        phone p = phoneDAO.getPhoneById(id);
        if (p != null) {
            return Response.ok(p).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/category/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhonesByCategory(@PathParam("id") int id) {
        ArrayList<phone> phones = phoneDAO.getPhonesByCategorieId(id);
        if (!phones.isEmpty()) {
            return Response.ok(phones).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPhone(phone p) {
        int result = phoneDAO.addPhone(p);
        if (result > 0) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePhone(@PathParam("id") int id, phone p) {
        p.setId(id);
        int result = phoneDAO.updatePhone(p);
        if (result > 0) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePhone(@PathParam("id") int id) {
        int result = phoneDAO.deletePhoneById(id);
        if (result > 0) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchPhones(@QueryParam("query") String query) {
        ArrayList<phone> phones = phoneDAO.searchPhones(query);
        if (!phones.isEmpty()) {
            return Response.ok(phones).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

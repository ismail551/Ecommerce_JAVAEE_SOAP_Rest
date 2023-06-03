/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jee.dao;

import com.mycompany.jee.dao.categorieDAO;
import com.mycompany.jee.entities.categorie;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/categories")
public class CategoryResource {

    private categorieDAO categoryDAO;

    public CategoryResource() {
        categoryDAO = new categorieDAO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        ArrayList<categorie> categories = categoryDAO.getcategories();
        if (categories != null) {
            return Response.ok(categories).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryById(@PathParam("id") int id) {
        categorie category = categoryDAO.getcategorieById(id);
        if (category != null) {
            return Response.ok(category).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategory(categorie category) {
        int status = categoryDAO.addcategorie(category);
        if (status == 1) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("id") int id, categorie category) {
        int status = categoryDAO.updateCategorie(id, category.getName());
        if (status == 1) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") int id) {
        int status = categoryDAO.deleteCategorieById(id);
        if (status == 1) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/phones/count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response countPhonesByCategory(@PathParam("id") int id) {
        int count = categoryDAO.countPhonesByCategorie(id);
        return Response.ok(count).build();
    }
}

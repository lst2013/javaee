package com.javaee.control;

import com.javaee.boundary.ProductService;
import com.javaee.convert.ProductConverter;
import com.javaee.entity.Product;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
public class ProductResource {

    @Inject
    private ProductConverter productConverter;

    @EJB
    private ProductService productService;

    @POST
    @Path("/createNew")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createProduct(JsonObject jsonObject) {
        Product product = productConverter.convertFromJson(jsonObject);
        productService.saveProduct(product);
    }

    @GET
    @Path("/allByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectsByName(@PathParam("name") String name) {
        List<Product> products = productService.findProductsByName(name);

        if (products.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        JsonObject jsonObject = productConverter.convertToJson(products);

        return Response.ok(jsonObject).build();
    }

    @GET
    @Path("/countNotAvailable")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getDisabledProductCount() {
        Long notAvailableProductCount = productService.findNotAvailableProductCount();
        return productConverter.convertToJson(notAvailableProductCount);
    }
}
package com.javaee.convert;

import com.javaee.entity.Product;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.List;

public class ProductConverter {

    private static final String CATEGORY = "category";

    private static final String NAME = "name";

    private static final String PRICE = "price";

    private static final String IS_AVAILABLE = "isAvailable";

    private static final String PRODUCTS_BY_NAME = "productsByName";

    private static final String NOT_AVAILABLE_PRODUCT_COUNT = "notAvailableProductCount";

    public Product convertFromJson(JsonObject jsonObject) {
        Product product = new Product();
        product.setCategory(jsonObject.getString(CATEGORY));
        product.setName(jsonObject.getString(NAME));
        product.setPrice(jsonObject.getJsonNumber(PRICE).bigDecimalValue());
        product.setAvailable(jsonObject.getBoolean(IS_AVAILABLE));

        return product;
    }

    public JsonObject convertToJson(List<Product> products) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Product product : products) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add(CATEGORY, product.getCategory());
            objectBuilder.add(NAME, product.getName());
            objectBuilder.add(PRICE, product.getPrice());
            objectBuilder.add(IS_AVAILABLE, product.isAvailable());

            arrayBuilder.add(objectBuilder);
        }

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add(PRODUCTS_BY_NAME, arrayBuilder);

        return objectBuilder.build();
    }

    public JsonObject convertToJson(Long notAvailableProductCount) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add(NOT_AVAILABLE_PRODUCT_COUNT, notAvailableProductCount);

        return objectBuilder.build();
    }
}
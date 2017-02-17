package com.tradefederation.wholesaler.inventory;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.tradefederation.wholesaler.retailer.WholesalerApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;

@Component
@DependsOn({"inventorySpecificationRepository", "wholesalerApplicationContext"})
@Profile("production")
public class ProductionInventoryRetriever {

    public ProductionInventoryRetriever() {
        initializeRestAssured();
        mapProductsToItemSpecifications(retrieveProductStream());
    }

    List<HashMap<String, Object>> retrieveProductStream() {
        return from(retrieveProducts()).get("products");
    }

    void mapProductsToItemSpecifications(List<HashMap<String, Object>> products) {
        ItemSpecificationRepository itemSpecificationRepository = WholesalerApplicationContext.compoentFor(ItemSpecificationRepository.class);

        products.forEach(productHashMap -> {
            String sku = (String) productHashMap.get("sku");
            String description = (String) productHashMap.get("description");
            itemSpecificationRepository.add(sku, description, BigDecimal.TEN);
        });
    }

    void initializeRestAssured() {
        RestAssured.port = 80;
        RestAssured.basePath = "/api";
        RestAssured.baseURI = "http://marketplace.tradefederation.space";
    }

    String retrieveProducts() {
        Response response = given()
                .when()
                .get("/catalog")
                .then().contentType(ContentType.JSON).extract().response();
        return response.asString();
    }

}

package com.tradefederation.wholesaler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.tradefederation.wholesaler.endpoint.ItemSpecificationDescription;
import com.tradefederation.wholesaler.endpoint.ReservationRequest;
import com.tradefederation.wholesaler.endpoint.RetailerDescription;
import com.tradefederation.wholesaler.inventory.ItemSpecification;
import com.tradefederation.wholesaler.inventory.ItemSpecificationId;
import com.tradefederation.wholesaler.reservation.Reservation;
import com.tradefederation.wholesaler.retailer.Retailer;
import com.tradefederation.wholesaler.retailer.RetailerId;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WholesalerFunctionalTest extends StartedApplicationTestBase {
    private static final String ITEM_SPECIFICATION_NAME = "xys1111";
    private static String RETAILER_NAME = "A Very Nice Retailer";

    @Test
    public void anAddedRetailerCanBeFound() {
        RetailerId retailerId = addRetailer();

        Retailer retailer = given()
                .pathParam("id", retailerId.value)
                .when()
                .get("/retailer/{id}")
                .as(Retailer.class);

        assertEquals(retailerId, retailer.getId());
    }

    @Test
    public void missingRetailerProducesFailure() {
        given()
                .pathParam("id", 0)
                .when()
                .get("/retailer/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void anAddedItemSpecificationCanBeFound() {
        ItemSpecificationId id = addItemSpecification();

        ItemSpecification spec = given()
                .pathParam("id", id.id)
                .when()
                .get("/itemSpecification/{id}")
                .as(ItemSpecification.class);

        assertEquals(id, spec.getId());
    }

    @Test
    public void anExistingRetailerCanPurchaseAQuantityOfExistingItems() {
        RetailerId retailerId = addRetailer();
        ItemSpecificationId specificationId = addItemSpecification();

        ReservationRequest request = buildReservationRequest(retailerId, specificationId, 3);

        Reservation reservation = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservation").as(Reservation.class);

        assertNotNull(reservation.getSecret());
        assertEquals(3, reservation.items.size());
        assertEquals(RETAILER_NAME, reservation.getRetailer().getName());
    }

    @Test
    public void shouldBeAbleToRetrieveAllSpecifications() throws Exception {
        addItemSpecification();
        addItemSpecification();
        addItemSpecification();

        Response response = given().when().get("itemSpecifications");

        // using just the built in api
        ItemSpecification[] specifications = response.getBody().as(ItemSpecification[].class);
        assertEquals(3, specifications.length);

        // using gson, which gives lambdas and more options on what we can do
        Gson gson = new Gson();
        List<ItemSpecification> specs
                = gson.fromJson(response.asString(), new TypeToken<List<ItemSpecification>>() {
        }.getType());
        assertEquals(3, specs.size());
    }

    private RetailerId addRetailer() {
        RetailerDescription retailerDescription = new RetailerDescription();
        retailerDescription.name = RETAILER_NAME;
        retailerDescription.callbackUrl = "http://localhost:8080";

        return given()
                .contentType(ContentType.JSON)
                .body(retailerDescription)
                .when().post("/retailer").as(RetailerId.class);
    }

    private ItemSpecificationId addItemSpecification() {
        ItemSpecificationDescription description = new ItemSpecificationDescription();
        description.name = ITEM_SPECIFICATION_NAME;
        description.description = "A nice item for all to buy";
        description.price = BigDecimal.ONE;

        return given()
                .contentType(ContentType.JSON)
                .body(description)
                .when().post("/itemSpecification").as(ItemSpecificationId.class);
    }

    private ReservationRequest buildReservationRequest(RetailerId retailerId1, ItemSpecificationId specificationId1, int quantity) {
        ReservationRequest request = new ReservationRequest();
        request.retailerId = retailerId1;
        request.itemSpecificationId = specificationId1;
        request.quantityToPurchase = quantity;
        return request;
    }
}

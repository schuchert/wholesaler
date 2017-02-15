package com.tradefederation.wholesaler.endpoint;

import com.tradefederation.wholesaler.Wholesaler;
import com.tradefederation.wholesaler.inventory.Item;
import com.tradefederation.wholesaler.inventory.ItemSpecification;
import com.tradefederation.wholesaler.inventory.ItemSpecificationId;
import com.tradefederation.wholesaler.reservation.Reservation;
import com.tradefederation.wholesaler.retailer.Retailer;
import com.tradefederation.wholesaler.retailer.RetailerId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "wholesaler", description = "The only interface into the wholesaler")
public class WholesalerController {
    @Autowired
    private Wholesaler wholesaler;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Retailer.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = Retailer.class),
            @ApiResponse(code = 404, message = "Retailer not found", response = Retailer.class)})
    @RequestMapping(value = "/retailers/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Retailer> getRetailerById(@ApiParam(required = true) @PathVariable("id") RetailerId id) {
        Optional<Retailer> retailer = wholesaler.retailerBy(id);
        if (retailer.isPresent()) {
            return ResponseEntity.ok().body(retailer.get());
        }
        return ResponseEntity.notFound().build();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "retailer created successfully", response = Retailer.class),
            @ApiResponse(code = 400, message = "Invalid callback URL", response = Retailer.class)})
    @RequestMapping(value = "/retailer",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RetailerId> createRetailer(@ApiParam() @RequestBody RetailerDescription retailer) {
        try {
            RetailerId retailerId = wholesaler.addRetailer(retailer.name, new URL(retailer.callbackUrl));
            return ResponseEntity.ok().body(retailerId);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().header("Invalid callbackUrl").build();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Item.class),
            @ApiResponse(code = 400, message = "Invalid Supplier ID supplied", response = Item.class),
            @ApiResponse(code = 400, message = "Invalid Item Specification ID supplied", response = Item.class)})
    @RequestMapping(value = "/item",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Item> purchase(@ApiParam(required = true) @RequestBody PurchaseRequest purchaseRequest) {
        Item item = wholesaler.purchase(purchaseRequest.retailerId, purchaseRequest.itemSpecificationId);
        return ResponseEntity.ok().body(item);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Item.class)})
    @RequestMapping(value = "/itemSpecification",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<ItemSpecificationId> createSpecification(@ApiParam() @RequestBody ItemSpecificationDescription specDescription) {
        ItemSpecificationId id = wholesaler.createItemSpecification(specDescription.name, specDescription.description, specDescription.price);
        return ResponseEntity.ok().body(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ItemSpecification.class, responseContainer = "List")})
    @RequestMapping(value = "/itemSpecifications",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<ItemSpecification>> getAllItemSpecifications() {
        List<ItemSpecification> itemSpecifications = wholesaler.allSpecifications();
        return ResponseEntity.ok().body(itemSpecifications);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ItemSpecification.class, responseContainer = "List")})
    @RequestMapping(value = "/retailers",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Retailer>> getAllRetailers() {
        List<Retailer> retailers = wholesaler.allRetailers();
        return ResponseEntity.ok().body(retailers);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Reservation.class)})
    @RequestMapping(value = "/reservation",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)

    public ResponseEntity<Reservation> reserve(@ApiParam() @RequestBody ReservationRequest request) {
        Reservation reservation = wholesaler.reserve(request.retailerId, request.itemSpecificationId, request.quantityToPurchase);
        return ResponseEntity.ok().body(reservation);
    }
}

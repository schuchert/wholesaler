package com.tradefederation.wholesaler.endpoint;

import com.tradefederation.wholesaler.Wholesaler;
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
import java.util.Optional;

@RestController
@Api(value = "wholesaler", description = "The only interface into the wholesaler")
public class WholesalerController {
    @Autowired
    Wholesaler wholesaler;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Retailer.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = Retailer.class),
            @ApiResponse(code = 404, message = "Pet not found", response = Retailer.class)})
    @RequestMapping(value = "/retailers/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Retailer> getRetailerById(@ApiParam(required = true) @PathVariable("id") RetailerId id) {
        Optional<Retailer> retailer = wholesaler.retailerBy(id);
        if (retailer.isPresent()) {
            return ResponseEntity.accepted().body(retailer.get());
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
            return ResponseEntity.accepted().body(retailerId);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().header("Invalid callbackUrl").build();
        }
    }
}

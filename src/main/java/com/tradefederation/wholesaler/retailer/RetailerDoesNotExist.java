package com.tradefederation.wholesaler.retailer;

public class RetailerDoesNotExist extends RuntimeException {
    public final RetailerId retailerId;

    public RetailerDoesNotExist(RetailerId retailerId) {
        this.retailerId = retailerId;
    }
}

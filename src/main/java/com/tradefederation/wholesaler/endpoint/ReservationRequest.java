package com.tradefederation.wholesaler.endpoint;

import com.tradefederation.wholesaler.inventory.ItemSpecificationId;
import com.tradefederation.wholesaler.retailer.RetailerId;

public class ReservationRequest {
    public RetailerId retailerId;
    public ItemSpecificationId itemSpecificationId;
    public int quantityToPurchase;
}

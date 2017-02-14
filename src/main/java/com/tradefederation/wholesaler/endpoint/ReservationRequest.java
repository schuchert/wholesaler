package com.tradefederation.wholesaler.endpoint;

import com.tradefederation.wholesaler.inventory.ItemSpecificationId;
import com.tradefederation.wholesaler.retailer.RetailerId;

public class ReservationRequest {
    RetailerId retailerId;
    ItemSpecificationId itemSpecificationId;
    int quantityToPurchase;
}

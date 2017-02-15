package com.tradefederation.wholesaler.endpoint;

import com.tradefederation.wholesaler.inventory.ItemSpecificationId;
import com.tradefederation.wholesaler.retailer.RetailerId;

class ReservationRequest {
    RetailerId retailerId;
    ItemSpecificationId itemSpecificationId;
    int quantityToPurchase;
}

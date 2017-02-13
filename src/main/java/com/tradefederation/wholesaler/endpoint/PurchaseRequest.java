package com.tradefederation.wholesaler.endpoint;

import com.tradefederation.wholesaler.inventory.ItemSpecificationId;
import com.tradefederation.wholesaler.retailer.RetailerId;

public class PurchaseRequest {
    public RetailerId retailerId;
    public ItemSpecificationId itemSpecificationId;
}

package com.tradefederation.wholesaler.inventory;

import com.tradefederation.wholesaler.retailer.Retailer;

import java.math.BigDecimal;

public class Item {
    public final ItemId id;
    public final ItemSpecification specification;
    public final Retailer retailer;
    public final BigDecimal price;

    public Item(ItemId id, ItemSpecification specification, Retailer retailer, BigDecimal price) {
        this.id = id;
        this.specification = specification;
        this.retailer = retailer;
        this.price = price;
    }
}

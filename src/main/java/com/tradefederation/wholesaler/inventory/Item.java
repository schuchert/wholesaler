package com.tradefederation.wholesaler.inventory;

import com.tradefederation.wholesaler.retailer.Retailer;

import java.math.BigDecimal;

public class Item {
    public ItemId id;
    public ItemSpecification specification;
    public Retailer retailer;
    public BigDecimal price;

    public Item() {
    }

    Item(ItemId id, ItemSpecification specification, Retailer retailer, BigDecimal price) {
        this.id = id;
        this.specification = specification;
        this.retailer = retailer;
        this.price = price;
    }

    public ItemId getId() {
        return id;
    }

    public ItemSpecification getSpecification() {
        return specification;
    }

    public Retailer getRetailer() {
        return retailer;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

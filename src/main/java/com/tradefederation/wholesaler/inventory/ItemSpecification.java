package com.tradefederation.wholesaler.inventory;

import java.math.BigDecimal;

public class ItemSpecification {
    public ItemSpecificationId id;
    public String name;
    public String description;
    public BigDecimal price;

    public ItemSpecification() {
    }

    public ItemSpecification(ItemSpecificationId id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}

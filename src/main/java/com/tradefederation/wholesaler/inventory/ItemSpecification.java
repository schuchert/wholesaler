package com.tradefederation.wholesaler.inventory;

import java.math.BigDecimal;

public class ItemSpecification {
    public final ItemSpecificationId id;
    public final String name;
    public final String description;
    public final BigDecimal price;

    public ItemSpecification(ItemSpecificationId id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}

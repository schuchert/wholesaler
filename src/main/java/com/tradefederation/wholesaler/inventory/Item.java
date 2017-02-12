package com.tradefederation.wholesaler.inventory;

import java.math.BigDecimal;

public class Item {
    public final ItemId id;
    public final ItemSpecification specification;
    public final BigDecimal price;

    public Item(ItemId id, ItemSpecification specification, BigDecimal price) {
        this.id = id;
        this.specification = specification;
        this.price = price;
    }
}

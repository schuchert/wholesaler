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

    public ItemSpecificationId getId() {
        return id;
    }

    public void setId(ItemSpecificationId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

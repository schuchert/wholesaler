package com.tradefederation.wholesaler.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemSpecification {
    public ItemSpecificationId id;
    public String sku;
    public String description;
    public BigDecimal price;
}

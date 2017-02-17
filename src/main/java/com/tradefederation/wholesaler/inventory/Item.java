package com.tradefederation.wholesaler.inventory;

import com.tradefederation.wholesaler.retailer.Retailer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Item {
    public ItemId id;
    public ItemSpecification specification;
    public Retailer retailer;
    public BigDecimal price;
}

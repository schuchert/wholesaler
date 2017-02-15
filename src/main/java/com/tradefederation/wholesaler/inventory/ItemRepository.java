package com.tradefederation.wholesaler.inventory;

import com.tradefederation.wholesaler.retailer.Retailer;

import java.util.Optional;

public interface ItemRepository {
    Item build(ItemSpecification itemSpecification, Retailer retailer);

    Optional<Item> findById(ItemId id);

    void clear();
}

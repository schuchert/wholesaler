package com.tradefederation.wholesaler.inventory;

import java.util.Optional;

public interface ItemRepository {
    Item build(ItemSpecification itemSpecification);

    Optional<Item> findById(ItemId id);
}

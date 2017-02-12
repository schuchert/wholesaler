package com.tradefederation.wholesaler.inventory;

import java.util.List;
import java.util.Optional;

public interface ItemSpecificationRepository {
    Optional<ItemSpecification> find(ItemSpecificationId itemSpecificationId);

    ItemSpecificationId add(String name, String description, String price);

    List<ItemSpecification> all();
}

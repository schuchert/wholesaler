package com.tradefederation.wholesaler.inventory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemSpecificationRepository {
    Optional<ItemSpecification> find(ItemSpecificationId itemSpecificationId);

    ItemSpecificationId add(String name, String description, BigDecimal price);

    List<ItemSpecification> all();
}

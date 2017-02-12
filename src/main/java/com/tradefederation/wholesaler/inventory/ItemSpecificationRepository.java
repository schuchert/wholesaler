package com.tradefederation.wholesaler.inventory;

import java.math.BigDecimal;
import java.util.Optional;

public interface ItemSpecificationRepository {
    Optional<ItemSpecification> find(ItemSpecificationId itemSpecificationId);

    ItemSpecification add(ItemSpecificationId itemSpecificationId, String name, String description, BigDecimal one);
}

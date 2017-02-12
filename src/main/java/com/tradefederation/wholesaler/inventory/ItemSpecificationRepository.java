package com.tradefederation.wholesaler.inventory;

import java.util.Optional;

public interface ItemSpecificationRepository {
    Optional<ItemSpecification> find(ItemSpecificationId itemSpecificationId);
}

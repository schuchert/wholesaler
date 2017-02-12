package com.hoeggsoftware.wholesaler.inventory;

import java.util.Optional;

public interface ItemSpecificationRepository {
    Optional<ItemSpecification> find(ItemSpecificationId itemSpecificationId);
}

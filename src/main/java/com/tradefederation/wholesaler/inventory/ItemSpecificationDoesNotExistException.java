package com.tradefederation.wholesaler.inventory;

public class ItemSpecificationDoesNotExistException extends RuntimeException {
    public final ItemSpecificationId itemSpecificationId;

    public ItemSpecificationDoesNotExistException(ItemSpecificationId itemSpecificationId) {
        this.itemSpecificationId = itemSpecificationId;
    }
}

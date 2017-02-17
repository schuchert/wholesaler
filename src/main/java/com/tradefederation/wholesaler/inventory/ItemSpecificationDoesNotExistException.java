package com.tradefederation.wholesaler.inventory;

public class ItemSpecificationDoesNotExistException extends RuntimeException {
    public final ItemSpecificationId itemSpecificationId;

    public ItemSpecificationDoesNotExistException(ItemSpecificationId itemSpecificationId) {
        super(String.format("Item Specification with id %d does not exist", itemSpecificationId.id));
        this.itemSpecificationId = itemSpecificationId;
    }

}

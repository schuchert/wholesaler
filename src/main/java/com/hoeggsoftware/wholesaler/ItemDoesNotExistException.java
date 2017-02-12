package com.hoeggsoftware.wholesaler;

public class ItemDoesNotExistException extends RuntimeException {
    public final ItemId itemId;

    public ItemDoesNotExistException(ItemId itemId) {
        this.itemId = itemId;
    }
}

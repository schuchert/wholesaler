package com.hoeggsoftware.wholesaler;

import java.util.Optional;

public interface ItemRepository {
    Optional<Item> find(ItemId itemId);
}

package com.tradefederation.wholesaler.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemSpecificationId {
    public long id;

    public ItemSpecificationId(String id) {
        this.id = Long.valueOf(id);
    }

    public ItemSpecificationId() {
    }
}

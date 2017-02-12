package com.tradefederation.wholesaler.inventory;

public class ItemId {
    public final long id;

    public ItemId(String value) {
        this.id = Long.valueOf(value);
    }

    public ItemId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemId itemId = (ItemId) o;

        return id == itemId.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}

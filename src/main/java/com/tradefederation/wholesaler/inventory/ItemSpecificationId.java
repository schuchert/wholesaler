package com.tradefederation.wholesaler.inventory;

public class ItemSpecificationId {
    public long id;

    public ItemSpecificationId() {
    }

    public ItemSpecificationId(String value) {
        id = Long.valueOf(value);
    }

    public ItemSpecificationId(long value) {
        this.id = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemSpecificationId that = (ItemSpecificationId) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}

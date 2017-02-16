package com.tradefederation.wholesaler.inventory;

public class ItemId {
    public long id;

    public ItemId() {
    }

    public ItemId(String value) {
        this.id = Long.valueOf(value);
    }

    ItemId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString() {
        return Long.toString(id);
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

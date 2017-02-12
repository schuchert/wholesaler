package com.tradefederation.wholesaler.retailer;

public class RetailerId {
    public final long value;

    public RetailerId(String value) {
        this.value = Long.valueOf(value);
    }

    public RetailerId(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RetailerId that = (RetailerId) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }
}

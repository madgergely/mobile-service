package com.epam.edu.mobilservice.request;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class MobilePartRequest implements Request {

    private static final AtomicLong counter = new AtomicLong();

    private final long id = counter.incrementAndGet();
    private final String partName;
    private int quantity;

    public MobilePartRequest(String partName, int quantity) {
        if (partName == null) {
            throw new IllegalArgumentException("partName cannot be NULL");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity cannot be negative");
        }
        this.partName = partName;
        this.quantity = quantity;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getPartName() {
        return partName;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.partName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MobilePartRequest other = (MobilePartRequest) obj;
        return Objects.equals(this.partName, other.partName);
    }

    @Override
    public String toString() {
        return String.format("MobilePartRequest-%d: part=%s, quantity=%d", id, partName, quantity);
    }

}

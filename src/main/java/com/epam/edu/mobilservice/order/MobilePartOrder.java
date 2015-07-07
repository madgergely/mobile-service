package com.epam.edu.mobilservice.order;

import com.epam.edu.mobilservice.mobile.part.MobilePart;

import java.util.concurrent.atomic.AtomicLong;

public class MobilePartOrder implements Order {

    private static final AtomicLong counter = new AtomicLong();

    private final long id = counter.incrementAndGet();
    private final MobilePart part;
    private final int quantity;

    public MobilePartOrder(MobilePart part, int quantity) {
        if (part == null) {
            throw new IllegalArgumentException("part cannot be NULL or empty");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity cannot be less than 1");
        }
        this.part = part;
        this.quantity = quantity;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public MobilePart getPart() {
        return part;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("MobilePartOrder-%d: part=%s, quantity=%d", id, part, quantity);
    }

}

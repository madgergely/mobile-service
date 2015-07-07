package com.epam.edu.mobilservice.store;

import com.epam.edu.mobilservice.mobile.part.MobilePart;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Stock {

    private final Map<MobilePart, Integer> stock = new HashMap<>();
    private final ReentrantLock stockLock = new ReentrantLock();

    public MobilePart getPart(String partName) {
        if (partName == null || partName.isEmpty()) {
            throw new IllegalArgumentException("partName cannot be NULL nor empty");
        }
        stockLock.lock();
        try {
            for (Map.Entry<MobilePart, Integer> part : stock.entrySet()) {
                if (part.getKey().getName().equals(partName)) {
                    int qty = part.getValue();
                    if (qty > 0) {
                        part.setValue(qty - 1);
                        return part.getKey();
                    } else {
                        return null;
                    }
                }
            }
            return null;
        } finally {
            stockLock.unlock();
        }
    }

    public void addPart(MobilePart part, int quantity) {
        if (part == null) {
            throw new IllegalArgumentException("part cannot be null");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity cannot be less than 1");
        }
        stockLock.lock();
        try {
            if (stock.containsKey(part)) {
                int qty = stock.get(part);
                stock.put(part, qty + quantity);
            } else {
                stock.put(part, quantity);
            }
        } finally {
            stockLock.unlock();
        }
    }
}

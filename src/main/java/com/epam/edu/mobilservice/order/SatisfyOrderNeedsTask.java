package com.epam.edu.mobilservice.order;

import com.epam.edu.mobilservice.mobile.part.MobilePart;
import com.epam.edu.mobilservice.mobile.part.MobilePartConverter;
import com.epam.edu.mobilservice.request.MobilePartRequest;

import java.util.concurrent.Callable;

public class SatisfyOrderNeedsTask implements Callable<Order> {

    private final MobilePartRequest request;

    public SatisfyOrderNeedsTask(MobilePartRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request cannot be NULL");
        }
        this.request = request;
    }

    @Override
    public Order call() {
        MobilePart part = MobilePartConverter.convert(request.getPartName());
        MobilePartOrder order = new MobilePartOrder(part, request.getQuantity());
        return order;
    }

}

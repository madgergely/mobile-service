package com.epam.edu.mobilservice.supplier;

import com.epam.edu.mobilservice.order.Order;
import com.epam.edu.mobilservice.request.MobilePartRequest;

import java.util.concurrent.ScheduledFuture;

public interface Supplier {

    void start();

    void shutdown();

    ScheduledFuture<Order> submitOrder(MobilePartRequest order);
}

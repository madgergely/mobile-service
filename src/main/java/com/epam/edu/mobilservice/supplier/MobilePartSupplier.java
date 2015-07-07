package com.epam.edu.mobilservice.supplier;

import com.epam.edu.mobilservice.order.Order;
import com.epam.edu.mobilservice.order.SatisfyOrderNeedsTask;
import com.epam.edu.mobilservice.request.MobilePartRequest;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MobilePartSupplier implements Supplier {

    private static final Logger logger = Logger.getLogger(MobilePartSupplier.class.getName());

    private final TaskScheduler scheduler = new TaskScheduler();

    @Override
    public ScheduledFuture<Order> submitOrder(MobilePartRequest request) {
        logger.logp(Level.FINE, "MobilePartSupplier.class", "submitOrder(MobilePartRequest request)", "submitting request " + request);
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be NULL");
        }

        return scheduler.scheduleTaskToRandomTime(new SatisfyOrderNeedsTask(request), 3, 6, TimeUnit.SECONDS);
    }

    @Override
    public void start() {
        logger.logp(Level.FINE, "MobilePartSupplier.class", "start()", "starting supplier...");
    }

    @Override
    public void shutdown() {
        logger.logp(Level.FINE, "MobilePartSupplier.class", "shutdown()", "shutting down supplier...");
        scheduler.shutdown();
    }
}

package com.epam.edu.mobilservice.store;

import com.epam.edu.mobilservice.mobile.part.MobilePart;
import com.epam.edu.mobilservice.order.Order;
import com.epam.edu.mobilservice.request.MobilePartRequest;
import com.epam.edu.mobilservice.request.RequestStatusChangeEvent;
import com.epam.edu.mobilservice.request.RequestStatusChangeEvent.Status;
import com.epam.edu.mobilservice.request.RequestStatusChangeListener;
import com.epam.edu.mobilservice.supplier.Supplier;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MobilePartStore implements Store {

    private static final Logger logger = Logger.getLogger(MobilePartStore.class.getName());
    private static final int DELIVERY_DELAY = 7;
    private static final double PART_MULTIPLIER = 1.4;

    private final Stock stock;
    private final Supplier supplier;
    private final ScheduledExecutorService deliveryService = Executors.newSingleThreadScheduledExecutor();
    private final Map<MobilePartRequest, Set<RequestStatusChangeListener>> orders = new HashMap<>();
    private final Lock orderLock = new ReentrantLock();

    public MobilePartStore(Stock stock, Supplier supplier) {
        if (stock == null) {
            throw new IllegalArgumentException("stock cannot be NULL");
        }
        if (supplier == null) {
            throw new IllegalArgumentException("supplier cannot be NULL");
        }
        this.stock = stock;
        this.supplier = supplier;
    }

    @Override
    public void start() {
        logger.logp(Level.FINE, "MobilePartStore.class", "start()", "starting store...");
        supplier.start();
        deliveryService.scheduleAtFixedRate(new DeliveryTask(), 5, DELIVERY_DELAY, TimeUnit.SECONDS);
    }

    @Override
    public void shutdown() {
        logger.logp(Level.FINE, "MobilePartStore.class", "shutdown()", "shutting down store...");
        supplier.shutdown();
        deliveryService.shutdown();
    }

    private void updateOrders(RequestStatusChangeListener listener, String partName) {
        orderLock.lock();
        try {
            for (Map.Entry<MobilePartRequest, Set<RequestStatusChangeListener>> entry : orders.entrySet()) {
                if (entry.getKey().getPartName().equals(partName)) {
                    int qty = entry.getKey().getQuantity();
                    entry.getKey().setQuantity(qty + 1);
                    entry.getValue().add(listener);
                    return;
                }
            }
            Set<RequestStatusChangeListener> listeners = new LinkedHashSet<>();
            listeners.add(listener);
            MobilePartRequest request = new MobilePartRequest(partName, 1);
            orders.put(request, listeners);
        } finally {
            orderLock.unlock();
        }
    }

    @Override
    public MobilePart requestPart(RequestStatusChangeListener listener, String partName) {
        logger.logp(Level.FINE, "MobilePartStore.class", "requestPart(RequestStatusChangeListener listener, String partName)", "requesting " + partName);
        MobilePart part = stock.getPart(partName);
        if (part != null) {
            return part;
        } else {
            updateOrders(listener, partName);
            listener.onRequestStatusChangedEvent(new RequestStatusChangeEvent(partName, Status.ORDERED));
            return null;
        }
    }

    private class DeliveryTask implements Runnable {

        private final Logger logger = Logger.getLogger(DeliveryTask.class.getName());

        private final Map<MobilePartRequest, Set<RequestStatusChangeListener>> currentOrders = new HashMap<>();

        @Override
        public void run() {
            prepareOrders();

            if (currentOrders.isEmpty()) {
                return;
            }

            Set<ScheduledFuture<Order>> orders = submitOrders();
            try {
                extractDeliveredOrders(orders);
            } catch (ExecutionException | InterruptedException ignored) {
            }
        }

        private void prepareOrders() {
            orderLock.lock();
            try {
                currentOrders.putAll(orders);
                orders.clear();
            } finally {
                orderLock.unlock();
            }

            for (Map.Entry<MobilePartRequest, Set<RequestStatusChangeListener>> entry : currentOrders.entrySet()) {
                MobilePartRequest partRequest = entry.getKey();

                partRequest.setQuantity((int) Math.round(partRequest.getQuantity() * PART_MULTIPLIER));
            }
        }

        private Set<ScheduledFuture<Order>> submitOrders() {
            Set<ScheduledFuture<Order>> submittedOrders = new LinkedHashSet<>();

            for (Map.Entry<MobilePartRequest, Set<RequestStatusChangeListener>> entry : currentOrders.entrySet()) {
                submittedOrders.add(supplier.submitOrder(entry.getKey()));
            }

            return submittedOrders;
        }

        private void extractDeliveredOrders(Set<ScheduledFuture<Order>> result) throws ExecutionException, InterruptedException {
            for (ScheduledFuture<Order> future : result) {
                Order order = future.get();
                stock.addPart(order.getPart(), order.getQuantity());

                RequestStatusChangeEvent event = new RequestStatusChangeEvent(order.getPart().getName(), Status.READY);
                fireRequestStatusChangeEvent(event);
            }
        }

        private void fireRequestStatusChangeEvent(RequestStatusChangeEvent event) {
            for (Map.Entry<MobilePartRequest, Set<RequestStatusChangeListener>> entry : currentOrders.entrySet()) {
                if (entry.getKey().getPartName().equals(event.getPartName())) {
                    for (RequestStatusChangeListener listener : entry.getValue()) {
                        listener.onRequestStatusChangedEvent(event);
                    }
                    return;
                }
            }
        }
    }
}

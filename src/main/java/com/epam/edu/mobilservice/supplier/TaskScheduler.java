package com.epam.edu.mobilservice.supplier;

import com.epam.edu.mobilservice.order.Order;

import java.util.Random;
import java.util.concurrent.*;

public class TaskScheduler {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final Random random = new Random();

    private ScheduledFuture<Order> scheduleTask(Callable<Order> task, long delay, TimeUnit timeUnit) {
        if (task == null) {
            throw new IllegalArgumentException("task cannot be NULL");
        }
        if (delay < 0) {
            throw new IllegalArgumentException("delay cannot be negative");
        }
        if (timeUnit == null) {
            throw new IllegalArgumentException("timeUnit cannot be NULL");
        }
        return executorService.schedule(task, delay, timeUnit);
    }

    public ScheduledFuture<Order> scheduleTaskToRandomTime(Callable<Order> task, long minDelay, long maxDelay, TimeUnit timeUnit) {

        long variance = maxDelay - minDelay;

        long delay = minDelay + random.nextInt((int) variance);

        return scheduleTask(task, delay, timeUnit);
    }
    
    public void shutdown() {
	executorService.shutdown();
    }

}

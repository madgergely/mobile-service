package com.epam.edu.mobilservice.client.executor;

import java.util.concurrent.*;

public class ClientThreadPoolExecutor extends ThreadPoolExecutor {

    public ClientThreadPoolExecutor(int numThreads) {
        super(numThreads, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue<>(numThreads, new PriorityTaskComparator()));
    }

    @Override
    protected <Worksheet> RunnableFuture<Worksheet> newTaskFor(final Callable<Worksheet> callable) {
        if (callable instanceof ImportantTask)
            return new PriorityTask<>(((ImportantTask) callable).getPriority(), callable);
        else
            return new PriorityTask<>(0, callable);
    }

    @Override
    protected <Worksheet> RunnableFuture<Worksheet> newTaskFor(final Runnable runnable, final Worksheet value) {
        if (runnable instanceof ImportantTask)
            return new PriorityTask<>(((ImportantTask) runnable).getPriority(), runnable, value);
        else
            return new PriorityTask<>(0, runnable, value);
    }
}

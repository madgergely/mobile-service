package com.epam.edu.mobilservice.client.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class PriorityTask<Worksheet> extends FutureTask<Worksheet> implements Comparable<PriorityTask<Worksheet>> {

    private final int priority;

    public PriorityTask(final int priority, final Callable<Worksheet> callable) {
        super(callable);
        this.priority = priority;
    }

    public PriorityTask(final int priority, final Runnable runnable, final Worksheet result) {
        super(runnable, result);
        this.priority = priority;
    }

    @Override
    public int compareTo(final PriorityTask<Worksheet> o) {
        final long diff = o.priority - priority;
        return 0 == diff ? 0 : 0 > diff ? -1 : 1;
    }
}

package com.epam.edu.mobilservice.client.executor;

import java.util.Comparator;

public class PriorityTaskComparator implements Comparator<Runnable> {

    @Override
    public int compare(final Runnable left, final Runnable right) {
        return ((PriorityTask) left).compareTo((PriorityTask) right);
    }
}

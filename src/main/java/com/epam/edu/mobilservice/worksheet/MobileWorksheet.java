package com.epam.edu.mobilservice.worksheet;

import com.epam.edu.mobilservice.client.ClientWithMobile;
import com.epam.edu.mobilservice.mobile.part.MobilePart;
import com.epam.edu.mobilservice.request.RequestStatusChangeEvent;
import com.epam.edu.mobilservice.request.RequestStatusChangeListener;
import com.epam.edu.mobilservice.service.Service;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MobileWorksheet extends Worksheet implements RequestStatusChangeListener {

    private final Lock lock = new ReentrantLock();
    private final Condition ORDER_SHIPPED_CONDITION = lock.newCondition();

    public MobileWorksheet(Service service, ClientWithMobile client) {
        super(service, client);
    }

    @Override
    protected void checkMobile() throws InterruptedException {
        setStatus(Status.RECEIVED);

        client.getMobile().check(this);

        if (badParts.isEmpty()) {
            finishMobile();
        } else {
            orderParts();
        }
    }

    @Override
    protected void orderParts() throws InterruptedException {
        setStatus(Status.WAITING_FOR_PARTS);

        MobilePart part;
        lock.lock();
        try {
            while (badParts.size() != newParts.size()) {
                for (MobilePart mp : badParts) {
                    if (!newParts.contains(mp)) {
                        part = service.requestPart(this, mp.getName());
                        if (part != null) {
                            newParts.add(part);
                        }
                    }
                }
                if (badParts.size() != newParts.size()) {
                    ORDER_SHIPPED_CONDITION.await();
                }
            }
        } finally {
            lock.unlock();
        }

        fixMobile();
    }

    @Override
    protected void fixMobile() {
        setStatus(Status.STARTED_TO_REPAIR);

        for (MobilePart part : newParts) {
            client.getMobile().replacePart(part);
        }

        finishMobile();
    }

    @Override
    protected void finishMobile() {
        setStatus(Status.FINISHED);
    }

    @Override
    public void onRequestStatusChangedEvent(RequestStatusChangeEvent event) {
        switch (event.getStatus()) {
            case READY: {
                lock.lock();
                try {
                    ORDER_SHIPPED_CONDITION.signal();
                } finally {
                    lock.unlock();
                }
                break;
            }
        }
    }

//    @Override
//    public int getPriority() {
//        return client.isRelativeOfBoss() ? Thread.MAX_PRIORITY : Thread.NORM_PRIORITY;
//    }
}

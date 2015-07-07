package com.epam.edu.mobilservice.client;

import com.epam.edu.mobilservice.mobile.Mobile;
import com.epam.edu.mobilservice.mobile.RandomMobileFactory;

import java.util.concurrent.atomic.AtomicLong;

public class Client implements ClientWithMobile {

    private static final AtomicLong counter = new AtomicLong();

    private final long id = counter.incrementAndGet();
    private final Mobile mobile;

    public Client() {
        mobile = new RandomMobileFactory().create();
    }

    public long getId() {
        return id;
    }

    public Mobile getMobile() {
        return mobile;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Client other = (Client) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return String.format("Client-%d, mobile=%s", id, mobile.getName());
    }
}

package com.epam.edu.mobilservice.request;

public class RequestStatusChangeEvent {

    private final String partName;
    private final Status status;

    public RequestStatusChangeEvent(String partName, Status status) {
        if (partName == null) {
            throw new IllegalArgumentException("partName cannot be NULL");
        }
        if (status == null) {
            throw new IllegalArgumentException("status cannot be NULL");
        }
        this.partName = partName;
        this.status = status;
    }

    public String getPartName() {
        return partName;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("RequestStatusChangeEvent: %s %s", partName, status.toString());
    }

    public enum Status {

        ORDERED {
            @Override
            public String toString() {
                return "Ordered";
            }
        },
        READY {
            @Override
            public String toString() {
                return "Ready";
            }
        };

        public abstract String toString();
    }

}

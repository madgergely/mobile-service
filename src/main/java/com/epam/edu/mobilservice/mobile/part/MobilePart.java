package com.epam.edu.mobilservice.mobile.part;

import com.epam.edu.mobilservice.mobile.MobileFactory.Manufacturer;


public abstract class MobilePart implements Visitable {

    protected final Manufacturer manufacturer;
    protected final boolean status;

    public MobilePart(Manufacturer manufacturer, boolean status) {
        if (manufacturer == null) {
            throw new IllegalArgumentException("manufacturer cannot be null");
        }
        this.manufacturer = manufacturer;
        this.status = status;
    }

    public abstract String getName();

    public abstract double getPrice();

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    @Override
    public String toString() {
        return String.format("%s, price=%f, status=%s", getName(), getPrice(), status);
    }
}

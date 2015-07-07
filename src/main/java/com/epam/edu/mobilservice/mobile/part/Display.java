package com.epam.edu.mobilservice.mobile.part;

import com.epam.edu.mobilservice.mobile.MobileFactory.Manufacturer;

public class Display extends MobilePart {

    private final String name;
    private final double price;

    public Display(Manufacturer manufacturer) {
        this(manufacturer, true);
    }

    public Display(Manufacturer manufacturer, boolean status) {
        super(manufacturer, status);
        this.name = String.format("%s Display", manufacturer.toString());
        this.price = 200 * manufacturer.getPriceMultiplier();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public boolean isDisplayWorking() {
        return status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Display other = (Display) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public void check(MobilePartVisitor service) {
        service.check(this);
    }

}

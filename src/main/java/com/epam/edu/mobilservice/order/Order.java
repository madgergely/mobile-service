package com.epam.edu.mobilservice.order;

import com.epam.edu.mobilservice.mobile.part.MobilePart;

public interface Order {

    long getId();

    int getQuantity();

    MobilePart getPart();

}

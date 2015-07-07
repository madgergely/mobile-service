package com.epam.edu.mobilservice.store;

import com.epam.edu.mobilservice.mobile.part.MobilePart;
import com.epam.edu.mobilservice.request.RequestStatusChangeListener;

public interface Store {

    void start();

    void shutdown();

    MobilePart requestPart(RequestStatusChangeListener listener, String partName);
}

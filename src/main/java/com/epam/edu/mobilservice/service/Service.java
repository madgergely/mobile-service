package com.epam.edu.mobilservice.service;

import com.epam.edu.mobilservice.client.ClientWithMobile;
import com.epam.edu.mobilservice.invoice.Invoice;
import com.epam.edu.mobilservice.mobile.part.MobilePart;
import com.epam.edu.mobilservice.request.RequestStatusChangeListener;

import java.util.concurrent.Future;

public interface Service {

    void start();

    void shutdown();

    Future<Invoice> repairMobileForClient(ClientWithMobile client);

    MobilePart requestPart(RequestStatusChangeListener listener, String partName);
}

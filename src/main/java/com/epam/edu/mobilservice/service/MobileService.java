package com.epam.edu.mobilservice.service;

import com.epam.edu.mobilservice.client.ClientWithMobile;
import com.epam.edu.mobilservice.invoice.Invoice;
import com.epam.edu.mobilservice.mobile.part.MobilePart;
import com.epam.edu.mobilservice.request.RequestStatusChangeListener;
import com.epam.edu.mobilservice.store.Store;
import com.epam.edu.mobilservice.worksheet.MobileWorksheet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MobileService implements Service {

    private static final Logger logger = Logger.getLogger(MobileService.class.getName());

    private final Store store;
    private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public MobileService(Store store) {
        if (store == null) {
            throw new IllegalArgumentException("store cannot be NULL");
        }
        this.store = store;
    }

    @Override
    public void start() {
        logger.logp(Level.FINE, "MobileService.class", "start()", "starting mobile service...");
        store.start();
    }

    @Override
    public void shutdown() {
        logger.logp(Level.FINE, "MobileService.class", "shutdown()", "shutting down mobile service...");
        store.shutdown();
        service.shutdown();
    }

    @Override
    public Future<Invoice> repairMobileForClient(ClientWithMobile client) {
        logger.logp(Level.FINE, "MobileService.class", "repairMobileForClient(Client client)", String.format("repairing mobile for client %s", client));
        MobileWorksheet worksheet = new MobileWorksheet(this, client);
        return service.submit(worksheet);
    }

    @Override
    public MobilePart requestPart(RequestStatusChangeListener listener, String partName) {
        logger.logp(Level.FINE, "MobileService.class", "requestPart(RequestStatusChangeListener listener, String partName)", String.format("requesting %s", partName));
        return store.requestPart(listener, partName);
    }

}

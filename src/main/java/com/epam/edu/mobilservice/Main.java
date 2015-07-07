package com.epam.edu.mobilservice;

import com.epam.edu.mobilservice.client.Client;
import com.epam.edu.mobilservice.invoice.Invoice;
import com.epam.edu.mobilservice.service.MobileService;
import com.epam.edu.mobilservice.service.Service;
import com.epam.edu.mobilservice.store.MobilePartStore;
import com.epam.edu.mobilservice.store.Stock;
import com.epam.edu.mobilservice.store.Store;
import com.epam.edu.mobilservice.supplier.MobilePartSupplier;
import com.epam.edu.mobilservice.supplier.Supplier;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    private final Service service;
    private final int clientNumber;
    private volatile int completedClients;

    public Main(Service service, int clientNumber) {
        this.service = service;
        this.clientNumber = clientNumber;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Stock stock = new Stock();
        Supplier supplier = new MobilePartSupplier();
        Store store = new MobilePartStore(stock, supplier);

        new Main(new MobileService(store), 30).startRepair();
    }

    private void startRepair() throws InterruptedException, ExecutionException {
        service.start();
        new Thread(new ProgressBar()).start();

        Set<Future<Invoice>> futures = sendMobilesToService();
        Set<Invoice> invoices = waitForCompletion(futures);

        System.out.println("\nPrinting invoices...\n");
        printInvoices(invoices);

        System.out.println("Shutting down...");
        service.shutdown();
    }

    private Set<Future<Invoice>> sendMobilesToService() {
        Set<Future<Invoice>> result = new LinkedHashSet<>();

        for (int i = 0; i < clientNumber; ++i) {
            result.add(service.repairMobileForClient(new Client()));
        }

        return result;
    }

    private Set<Invoice> waitForCompletion(Set<Future<Invoice>> futures) throws InterruptedException, ExecutionException {
        Set<Invoice> invoices = new LinkedHashSet<>();

        for (Future<Invoice> future : futures) {
            Invoice invoice = future.get();
            invoices.add(invoice);
            completedClients++;
        }

        return invoices;
    }

    private void printInvoices(Set<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            System.out.println(invoice.printInvoice());
        }
    }

    private class ProgressBar implements Runnable {

        private final char[] cursorStates = {'-', '\\', '|', '/'};

        @Override
        public void run() {
            int i = 0;
            while (completedClients < clientNumber) {

                System.out.printf("\rCompleted clients %d / %d %s", completedClients, clientNumber, cursorStates[i++]);

                i = i > 3 ? 0 : i;

                try {
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {
                }
            }
        }

    }
}

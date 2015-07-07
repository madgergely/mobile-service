package com.epam.edu.mobilservice.worksheet;

import com.epam.edu.mobilservice.client.ClientWithMobile;
import com.epam.edu.mobilservice.invoice.Invoice;
import com.epam.edu.mobilservice.mobile.Mobile;
import com.epam.edu.mobilservice.mobile.part.*;
import com.epam.edu.mobilservice.service.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Worksheet implements MobilePartVisitor, Callable<Invoice> {

    private static final Logger logger = Logger.getLogger(Worksheet.class.getName());
    private static final AtomicLong counter = new AtomicLong();

    protected final long id = counter.incrementAndGet();
    protected final Service service;
    protected final Set<MobilePart> badParts = new LinkedHashSet<>();
    protected final Set<MobilePart> newParts = new LinkedHashSet<>();
    protected final ClientWithMobile client;

    protected Status status = Status.RECEIVED;
    protected long startTime;

    public Worksheet(Service service, ClientWithMobile client) {
        if (service == null) {
            throw new IllegalArgumentException("service cannot be NULL");
        }
        if (client == null || client.getMobile() == null) {
            throw new IllegalArgumentException("client nor mobile cannot be NULL");
        }
        this.service = service;
        this.client = client;
    }

    @Override
    public Invoice call() throws InterruptedException {
        startTime = System.currentTimeMillis();
        checkMobile();
        return createInvoice();
    }

    private Invoice createInvoice() {
        return new Invoice(client.getMobile(), newParts, (System.currentTimeMillis() - startTime) / 1000);
    }

    protected abstract void checkMobile() throws InterruptedException;

    protected abstract void orderParts() throws InterruptedException;

    protected abstract void fixMobile();

    protected abstract void finishMobile();

    public Mobile getMobile() {
        return client.getMobile();
    }

    public Status getStatus() {
        return status;
    }

    protected void setStatus(Status status) {
        logger.log(Level.FINE, String.format("Worksheet %d - %s\n", id, status));
        this.status = status;
    }

    @Override
    public void check(Display display) {
        if (!display.isDisplayWorking()) {
            badParts.add(display);
        }
    }

    @Override
    public void check(Keyboard keyboard) {
        if (!keyboard.isKeyboardWorking()) {
            badParts.add(keyboard);
        }
    }

    @Override
    public void check(Microphone microphone) {
        if (!microphone.isMicrophoneWorking()) {
            badParts.add(microphone);
        }
    }

    @Override
    public void check(Motherboard motherboard) {
        if (!motherboard.isMotherboardWorking()) {
            badParts.add(motherboard);
        }
    }

    @Override
    public void check(PowerSwitch powerSwitch) {
        if (!powerSwitch.isPowerSwitchWorking()) {
            badParts.add(powerSwitch);
        }
    }

    @Override
    public void check(Speaker speaker) {
        if (!speaker.isSpeakerWorking()) {
            badParts.add(speaker);
        }
    }

    @Override
    public void check(VolumeButton volumeButton) {
        if (!volumeButton.isVolumeButtonWorking()) {
            badParts.add(volumeButton);
        }
    }

    @Override
    public String toString() {
        return "Worksheet{" + "id=" + id + '}';
    }

    public enum Status {

        RECEIVED,
        WAITING_FOR_PARTS,
        STARTED_TO_REPAIR,
        FINISHED
    }

}

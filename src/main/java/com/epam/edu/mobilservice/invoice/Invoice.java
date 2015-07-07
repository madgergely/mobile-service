package com.epam.edu.mobilservice.invoice;

import com.epam.edu.mobilservice.mobile.Mobile;
import com.epam.edu.mobilservice.mobile.part.MobilePart;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public class Invoice implements Comparable<Invoice> {

    private static final AtomicLong counter = new AtomicLong();

    private final long id = counter.incrementAndGet();
    private final Mobile mobile;
    private final Collection<MobilePart> repairedParts;
    private long repairedTimeInSec;

    public Invoice(Mobile mobile, Collection<MobilePart> repairedParts, long repairTimeInSec) {
        if (mobile == null) {
            throw new IllegalArgumentException("mobile cannot be NULL");
        }
        if (repairedParts == null) {
            throw new IllegalArgumentException("repairedParts cannot be NULL");
        }
        if (repairTimeInSec < 0) {
            throw new IllegalArgumentException("repairTimeInSec cannot be negative");
        }
        this.mobile = mobile;
        this.repairedTimeInSec = repairTimeInSec;
        this.repairedParts = repairedParts;
    }

    public String printInvoice() {
        StringBuilder builder = new StringBuilder();

        appendHeaderToInvoice(builder);
        appendBodyToInvoice(builder);
        appendFooterToInvoice(builder);

        return builder.toString();
    }

    private void appendHeaderToInvoice(StringBuilder builder) {
        builder.append("Invoice-").append(id).append('\n');
        builder.append("Mobile: ").append(mobile.getName()).append('\n');
        builder.append("Repaired in ").append(repairedTimeInSec).append(" sec.").append('\n');
    }

    private void appendBodyToInvoice(StringBuilder builder) {
        if (repairedParts.isEmpty()) {
            appendEmptyBodyToInvoice(builder);
            return;
        }
        double totalPrice = 0;

        builder.append("Replaced parts:").append('\n');
        builder.append("----------------------------------------").append('\n');
        for (MobilePart part : repairedParts) {
            builder.append(part.getName()).append(": ").append(part.getPrice()).append('\n');
            totalPrice += part.getPrice();
        }

        builder.append("----------------------------------------").append('\n');
        builder.append("Total price: ").append(totalPrice).append('\n');
    }

    private void appendEmptyBodyToInvoice(StringBuilder builder) {
        builder.append("Nothing to repair").append('\n');
    }

    private void appendFooterToInvoice(StringBuilder builder) {
        builder.append('\n');
    }

    @Override
    public int compareTo(Invoice o) {
        return Long.compare(id, o.id);
    }
}

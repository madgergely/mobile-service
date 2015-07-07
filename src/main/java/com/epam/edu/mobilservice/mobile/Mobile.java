package com.epam.edu.mobilservice.mobile;

import com.epam.edu.mobilservice.mobile.part.MobilePart;
import com.epam.edu.mobilservice.mobile.part.MobilePartVisitor;
import com.epam.edu.mobilservice.mobile.part.Visitable;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Mobile implements Visitable {

    protected final Set<MobilePart> parts;

    protected Mobile(Set<MobilePart> parts) {
        if (parts == null || parts.isEmpty()) {
            throw new IllegalArgumentException("parts cannot be NULL nor empty");
        }
        this.parts = new LinkedHashSet<>(parts);
    }

    public abstract String getName();

    public Set<MobilePart> getParts() {
        return Collections.unmodifiableSet(parts);
    }

    public boolean replacePart(MobilePart part) {
        boolean exists = parts.remove(part);
        if (exists) {
            parts.add(part);
        }
        return exists;
    }

    @Override
    public void check(MobilePartVisitor visitor) {
        for (MobilePart part : parts) {
            part.check(visitor);
        }
    }
}
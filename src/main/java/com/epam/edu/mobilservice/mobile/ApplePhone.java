package com.epam.edu.mobilservice.mobile;

import com.epam.edu.mobilservice.mobile.MobileFactory.Manufacturer;
import com.epam.edu.mobilservice.mobile.part.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ApplePhone extends Mobile {

    private final Manufacturer manufacturer;
    private final AppleModel model;

    private ApplePhone(Manufacturer manufacturer, AppleModel model, Set<MobilePart> parts) {
        super(parts);
        if (manufacturer == null) {
            throw new IllegalArgumentException("manufacturer cannot be null");
        }
        if (model == null) {
            throw new IllegalArgumentException("model cannot be null");
        }
        this.manufacturer = manufacturer;
        this.model = model;
    }

    @Override
    public String getName() {
        return String.format("%s %s", manufacturer.toString(), model);
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    @Override
    public String toString() {
        return manufacturer + ", model=" + model + ", parts=" + parts + '}';
    }

    public enum AppleModel {

        IPHONE_4S {
            @Override
            public String toString() {
                return "iPhone 4s";
            }

        },
        IPHONE_5S {
            @Override
            public String toString() {
                return "iPhone 5s";
            }

        },
        IPHONE_5C {
            @Override
            public String toString() {
                return "iPhone 5c";
            }

        };

        @Override
        public abstract String toString();
    }

    public static class Factory implements MobileFactory {

        private final Random random = new Random();

        @Override
        public ApplePhone create() {
            AppleModel model = AppleModel.values()[random.nextInt(AppleModel
                    .values().length)];

            Set<MobilePart> parts = new HashSet<>();
            parts.add(new Display(Manufacturer.APPLE, random.nextBoolean()));
            parts.add(new Microphone(Manufacturer.APPLE, random.nextBoolean()));
            parts.add(new Motherboard(Manufacturer.APPLE, random.nextBoolean()));
            parts.add(new Keyboard(Manufacturer.APPLE, random.nextBoolean()));
            parts.add(new PowerSwitch(Manufacturer.APPLE, random.nextBoolean()));
            parts.add(new Speaker(Manufacturer.APPLE, random.nextBoolean()));
            parts.add(new VolumeButton(Manufacturer.APPLE, random.nextBoolean()));

            return new ApplePhone(Manufacturer.APPLE, model, parts);
        }

    }
}

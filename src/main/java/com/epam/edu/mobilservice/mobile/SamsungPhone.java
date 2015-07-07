package com.epam.edu.mobilservice.mobile;

import com.epam.edu.mobilservice.mobile.MobileFactory.Manufacturer;
import com.epam.edu.mobilservice.mobile.part.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SamsungPhone extends Mobile {

    private final Manufacturer manufacturer;
    private final SamsungModel model;

    private SamsungPhone(Manufacturer manufacturer, SamsungModel model, Set<MobilePart> parts) {
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

    public enum SamsungModel {
        GALAXY_S3 {
            @Override
            public String toString() {
                return "Galaxy S3";
            }

        },
        GALAXY_S4 {
            @Override
            public String toString() {
                return "Galaxy S4";
            }

        },
        GALAXY_Note {
            @Override
            public String toString() {
                return "Galaxy Note";
            }

        };

        @Override
        public abstract String toString();
    }

    public static class Factory implements MobileFactory {

        private final Random random = new Random();

        @Override
        public SamsungPhone create() {
            SamsungModel model = SamsungModel.values()[random
                    .nextInt(SamsungModel.values().length)];

            Set<MobilePart> parts = new HashSet<>();
            parts.add(new Display(Manufacturer.SAMSUNG, random.nextBoolean()));
            parts.add(new Microphone(Manufacturer.SAMSUNG, random.nextBoolean()));
            parts.add(new Motherboard(Manufacturer.SAMSUNG, random.nextBoolean()));
            parts.add(new PowerSwitch(Manufacturer.SAMSUNG, random.nextBoolean()));
            parts.add(new Keyboard(Manufacturer.SAMSUNG, random.nextBoolean()));
            parts.add(new Speaker(Manufacturer.SAMSUNG, random.nextBoolean()));
            parts.add(new VolumeButton(Manufacturer.SAMSUNG, random.nextBoolean()));

            return new SamsungPhone(Manufacturer.SAMSUNG, model, parts);
        }
    }
}

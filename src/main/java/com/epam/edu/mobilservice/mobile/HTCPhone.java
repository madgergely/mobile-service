package com.epam.edu.mobilservice.mobile;

import com.epam.edu.mobilservice.mobile.MobileFactory.Manufacturer;
import com.epam.edu.mobilservice.mobile.part.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HTCPhone extends Mobile {

    private final Manufacturer manufacturer;
    private final HTCModel model;

    private HTCPhone(Manufacturer manufacturer, HTCModel model, Set<MobilePart> parts) {
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

    public enum HTCModel {
        ONE {
            @Override
            public String toString() {
                return "One";
            }

        },
        DESIRE_610 {
            @Override
            public String toString() {
                return "Desire 610";
            }

        },
        ONE_MINI {
            @Override
            public String toString() {
                return "One Mini";
            }

        };

        @Override
        public abstract String toString();
    }

    public static class Factory implements MobileFactory {

        private final Random random = new Random();

        @Override
        public HTCPhone create() {
            HTCModel model = HTCModel.values()[random
                    .nextInt(HTCModel.values().length)];

            Set<MobilePart> parts = new HashSet<>();
            parts.add(new Display(Manufacturer.HTC, random.nextBoolean()));
            parts.add(new Microphone(Manufacturer.HTC, random.nextBoolean()));
            parts.add(new Motherboard(Manufacturer.HTC, random.nextBoolean()));
            parts.add(new Keyboard(Manufacturer.HTC, random.nextBoolean()));
            parts.add(new PowerSwitch(Manufacturer.HTC, random.nextBoolean()));
            parts.add(new Speaker(Manufacturer.HTC, random.nextBoolean()));
            parts.add(new VolumeButton(Manufacturer.HTC, random.nextBoolean()));

            return new HTCPhone(Manufacturer.HTC, model, parts);
        }

    }
}

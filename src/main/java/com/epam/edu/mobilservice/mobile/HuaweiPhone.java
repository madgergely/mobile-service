package com.epam.edu.mobilservice.mobile;

import com.epam.edu.mobilservice.mobile.MobileFactory.Manufacturer;
import com.epam.edu.mobilservice.mobile.part.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HuaweiPhone extends Mobile {

    private final Manufacturer manufacturer;
    private final HuaweiModel model;

    private HuaweiPhone(Manufacturer manufacturer, HuaweiModel model, Set<MobilePart> parts) {
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

    public enum HuaweiModel {
        ASCEND_P7 {
            @Override
            public String toString() {
                return "Ascend P7";
            }

        },
        ASCEND_P2 {
            @Override
            public String toString() {
                return "Ascend P2";
            }

        },
        ASCEND_G630 {
            @Override
            public String toString() {
                return "Ascend G630";
            }

        };

        @Override
        public abstract String toString();
    }

    public static class Factory implements MobileFactory {

        private final Random random = new Random();

        @Override
        public HuaweiPhone create() {
            HuaweiModel model = HuaweiModel.values()[random.nextInt(HuaweiModel
                    .values().length)];

            Set<MobilePart> parts = new HashSet<>();
            parts.add(new Display(Manufacturer.HUAWEI, random.nextBoolean()));
            parts.add(new Microphone(Manufacturer.HUAWEI, random.nextBoolean()));
            parts.add(new Motherboard(Manufacturer.HUAWEI, random.nextBoolean()));
            parts.add(new PowerSwitch(Manufacturer.HUAWEI, random.nextBoolean()));
            parts.add(new Keyboard(Manufacturer.HUAWEI, random.nextBoolean()));
            parts.add(new Speaker(Manufacturer.HUAWEI, random.nextBoolean()));
            parts.add(new VolumeButton(Manufacturer.HUAWEI, random.nextBoolean()));

            return new HuaweiPhone(Manufacturer.HUAWEI, model, parts);
        }

    }
}

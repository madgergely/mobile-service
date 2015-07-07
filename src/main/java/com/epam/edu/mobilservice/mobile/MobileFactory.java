package com.epam.edu.mobilservice.mobile;

public interface MobileFactory {

    Mobile create();

    enum Manufacturer {

        SAMSUNG {
            @Override
            public double getPriceMultiplier() {
                return 1.5;
            }

        },
        APPLE {
            @Override
            public double getPriceMultiplier() {
                return 1.8;
            }

        },
        HTC {
            @Override
            public double getPriceMultiplier() {
                return 1;
            }

        },
        HUAWEI {
            @Override
            public double getPriceMultiplier() {
                return 0.8;
            }

        };

        public abstract double getPriceMultiplier();
    }
}

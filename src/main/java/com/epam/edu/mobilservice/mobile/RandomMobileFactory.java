package com.epam.edu.mobilservice.mobile;

import java.util.Random;

public class RandomMobileFactory implements MobileFactory {

    private final MobileFactory appleFactory = new ApplePhone.Factory();
    private final MobileFactory HTCFactory = new HTCPhone.Factory();
    private final MobileFactory huaweiFactory = new HuaweiPhone.Factory();
    private final MobileFactory samsungFactory = new SamsungPhone.Factory();

    @Override
    public Mobile create() {
        int num = new Random().nextInt(4);

        switch (num) {
            case 0:
                return appleFactory.create();
            case 1:
                return HTCFactory.create();
            case 2:
                return huaweiFactory.create();
            default:
                return samsungFactory.create();
        }
    }

}

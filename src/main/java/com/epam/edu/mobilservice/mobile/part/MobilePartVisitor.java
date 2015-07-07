package com.epam.edu.mobilservice.mobile.part;

public interface MobilePartVisitor {

    void check(Display display);

    void check(Keyboard keyboard);

    void check(Microphone microphone);

    void check(Motherboard motherboard);

    void check(PowerSwitch powerSwitch);

    void check(Speaker speaker);

    void check(VolumeButton volumeButton);
}

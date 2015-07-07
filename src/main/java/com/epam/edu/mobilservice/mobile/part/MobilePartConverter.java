package com.epam.edu.mobilservice.mobile.part;

import com.epam.edu.mobilservice.mobile.MobileFactory.Manufacturer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobilePartConverter {

    public static MobilePart convert(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        Manufacturer manufacturer;
        if (name.startsWith(Manufacturer.APPLE.toString())) {
            manufacturer = Manufacturer.APPLE;
        } else if (name.startsWith(Manufacturer.HTC.toString())) {
            manufacturer = Manufacturer.HTC;
        } else if (name.startsWith(Manufacturer.HUAWEI.toString())) {
            manufacturer = Manufacturer.HUAWEI;
        } else if (name.startsWith(Manufacturer.SAMSUNG.toString())) {
            manufacturer = Manufacturer.SAMSUNG;
        } else {
            throw new IllegalStateException("no manufacturer with the given name: " + name);
        }

        if (name.endsWith("Display")) {
            return new Display(manufacturer);
        } else if (name.endsWith("Keyboard")) {
            return new Keyboard(manufacturer);
        } else if (name.endsWith("Microphone")) {
            return new Microphone(manufacturer);
        } else if (name.endsWith("Motherboard")) {
            return new Motherboard(manufacturer);
        } else if (name.endsWith("Power Switch")) {
            return new PowerSwitch(manufacturer);
        } else if (name.endsWith("Speaker")) {
            return new Speaker(manufacturer);
        } else if (name.endsWith("Volume Button")) {
            return new VolumeButton(manufacturer);
        } else {
            throw new IllegalStateException("no mobile part with the given name: " + name);
        }
    }
}

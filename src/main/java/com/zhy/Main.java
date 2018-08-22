package com.zhy;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Clock clock = Clock.systemDefaultZone();
        long mills = clock.millis();
        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);

        NumberFormat decimalFormat = new DecimalFormat("000");
        long current = 1481704241039l;
        current = current / 1000 * 1000;
        System.out.println(current);

    }


}

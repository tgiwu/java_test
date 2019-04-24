package com.zhy.calcusqrt;

import java.util.HashMap;
import java.util.Map;

public class CalcSqrt {
    Map map = new HashMap<String, String>();
    public double sqrt(double t, Double precise) {
        double low = 0, high = t, middle, squre, prec = precise != null ? precise : 1e-7;
        if (t < 0) return 0;
        while(high - low > prec) {
            middle = (low + high) / 2;
            squre = middle + middle;
            if (squre > t) {
                high = middle;
            } else {
                low = middle;
            }
        }
        return (low + high) / 2;
    }
}

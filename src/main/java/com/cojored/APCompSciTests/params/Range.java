package com.cojored.APCompSciTests.params;

public class Range extends Value<Integer> {
    public final int min;
    public final int max;
    public final boolean inclusive;

    public Range(int min, int max, boolean inclusive) {
        super();
        this.min = min;
        this.max = max;
        this.inclusive = inclusive;
    }

    public Integer getValue() {
        return ((int) (Math.random() * (max - min) + (inclusive ? 1 : 0))) + min;
    }

    public String toString() {
        return min + " - " + max;
    }
}

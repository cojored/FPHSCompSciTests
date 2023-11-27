package com.cojored.FPHSCompSciTests.params;

public class Random extends Value<String> {
    public final String[] array;

    public Random(String[] array) {
        super();
        this.array = array;
    }

    public String getValue() {
        return array[(int) (Math.random() * array.length)];
    }

    public String toString() {
        return String.join("|", array);
    }
}

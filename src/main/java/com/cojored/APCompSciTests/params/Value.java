package com.cojored.APCompSciTests.params;

public class Value<T> {
    public T value;

    Value(T v) {
        value = v;
    }

    Value() {
        value = null;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

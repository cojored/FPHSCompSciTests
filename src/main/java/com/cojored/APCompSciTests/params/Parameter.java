package com.cojored.APCompSciTests.params;

public class Parameter {
    private final Value<?>[] params;

    public Parameter(Value<?>[] params) {
        this.params = params;
    }

    public String getValue() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            s.append(params[i].getValue());
            if (i < params.length - 1) s.append("\n");
        }
        return s.toString();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            s.append(params[i].toString());
            if (i < params.length - 1) s.append(" ");
        }
        return s.toString();
    }
}

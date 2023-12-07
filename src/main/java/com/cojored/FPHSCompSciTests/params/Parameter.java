package com.cojored.FPHSCompSciTests.params;

import java.util.ArrayList;

public class Parameter {
    private final Value<?>[] params;
    private final String methodName;
    private String typeString;
    private Object[] value = null;
    private Parameter constructor;

    public Parameter(Value<?>[] params) {
        this.params = params;
        this.typeString = null;
        this.methodName = null;
        this.constructor = null;
    }

    public Parameter(Value<?>[] params, String methodName, String typeString) {
        this.params = params;
        this.methodName = methodName;
        this.typeString = typeString;
        if (this.methodName != null) this.constructor = new Parameter(new Value<?>[]{});
        else this.constructor = null;
    }

    public Parameter getConstructor() {
        return constructor;
    }

    public Parameter setConstructor(Parameter c) {
        constructor = c;
        return this;
    }

    public Parameter setParamType(String s) {
        typeString = s;
        return this;
    }

    public String getValue() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            s.append(params[i].getValue());
            if (i < params.length - 1) s.append("\n");
        }
        return s.toString();
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getListValue() {
        if (value == null) {
            ArrayList<Object> obj = new ArrayList<>();
            for (Value<?> param : params) {
                obj.add(param.getValue());
            }
            value = obj.toArray();
        }
        return value;
    }

    public Object[] getListValue(Class<?> cls) {
        getListValue();
        for (int i = 0; i < params.length; i++)
            if (params[i] instanceof Constructor) {
                value[i] = (((Constructor) params[i]).getValue(cls));
            }
        return getListValue();
    }

    public String getTypeString() {
        return typeString;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        if (methodName != null) s.append(methodName).append("(");
        for (int i = 0; i < params.length; i++) {
            s.append(params[i].toString());
            if (i < params.length - 1) s.append(", ");
        }
        if (methodName != null) s.append(")");
        return s.toString();
    }
}

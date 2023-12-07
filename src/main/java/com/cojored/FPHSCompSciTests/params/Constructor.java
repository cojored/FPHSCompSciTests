package com.cojored.FPHSCompSciTests.params;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import static com.cojored.FPHSCompSciTests.ClassTest.getParamTypesString;

public class Constructor extends Value<Parameter> {
    private final String className;

    public Constructor(Parameter p, String className) {
        super(p);
        this.className = className;
    }

    private java.lang.reflect.Constructor<?> getConstructor(Class<?> cls, String typeString) {
        for (java.lang.reflect.Constructor<?> con : cls.getConstructors())
            if (getParamTypesString(con).equals(typeString)) return con;
        return null;
    }

    public Object getValue(Class<?> cls) {
        if (className.equals("{class}")) {
            try {
                return Objects.requireNonNull(getConstructor(cls, value.getTypeString())).newInstance(value.getListValue(cls));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        //TODO: Implement other classes
        return null;
    }
}

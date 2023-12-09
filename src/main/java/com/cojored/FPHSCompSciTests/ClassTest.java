package com.cojored.FPHSCompSciTests;

import com.cojored.FPHSCompSciTests.params.Parameter;
import com.cojored.FPHSCompSciTests.utils.ReturnValues;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ClassTest {
    private static List<Parameter> params;
    private static List<Parameter> constructors;
    private static Class<?> testClass;
    private static Class<?> workingClass;
    private final PrintStream originalOut = System.out;
    private final Parameter param;
    private Object workingInstance;
    private Object testInstance;

    public ClassTest(Parameter p) {
        param = p;
    }

    public static void setParams(Parameter[] p) {
        params = Arrays.asList(p);
    }

    public static void setClass(Class<?> c) {
        testClass = c;
    }

    public static void setConstructors(Parameter[] c) {
        constructors = Arrays.asList(c);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Parameter> data() {
        ArrayList<Parameter> pArr = new ArrayList<>();
        for (Parameter c : constructors)
            for (Parameter p : params) {
                p.setConstructor(c);
                pArr.add(p);
            }
        return pArr;
    }

    public static String getParamTypesString(Executable e) {
        ArrayList<String> types = new ArrayList<>();
        for (Type t : e.getGenericParameterTypes())
            types.add(t.getTypeName());

        return String.join(", ", types);
    }

    private Method getTargetMethod(Class<?> cls, String name, String typeStr) {
        if (Objects.equals(typeStr, "{class}")) typeStr = cls.getName();
        for (Method method : cls.getMethods())
            if (method.getName().equals(name)) if (typeStr.equals(getParamTypesString(method))) return method;


        return null;
    }

    private Constructor<?> getTargetConstructor(Class<?> cls) {
        for (Constructor<?> con : cls.getConstructors())
            if (getParamTypesString(con).equals(param.getConstructor().getTypeString())) return con;

        return null;
    }

    @Before
    public void setUp() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        workingClass = Thread.currentThread().getContextClassLoader().loadClass("com.cojored.FPHSCompSciTests.programs." + testClass.getName().replaceAll("_.{3}", ""));
        workingInstance = Objects.requireNonNull(getTargetConstructor(workingClass)).newInstance(param.getConstructor().getListValue(workingClass));
        testInstance = Objects.requireNonNull(getTargetConstructor(testClass)).newInstance(param.getConstructor().getListValue(testClass));
    }

    @Test
    public void test() {
        try {
            ReturnValues r = runMethod(param.getMethodName(), param.getTypeString(), false);

            assertEquals(param.getMethodName() + "(" + param.getTypeString() + ")", r.working, r.test);
            assertEquals(param.getMethodName() + "(" + param.getTypeString() + ")", r.outputWorking, r.outputTest);

            for (Method method : workingClass.getMethods()) {
                if (method.getAnnotation(com.cojored.FPHSCompSciTests.annotations.Accessor.class) != null) {
                    ReturnValues rAccess = runMethod(method.getName(), getParamTypesString(method), true);
                    assertEquals(method.getName() + "(" + getParamTypesString(method) + ")", rAccess.working, rAccess.test);
                    assertEquals(method.getName() + "(" + getParamTypesString(method) + ")", rAccess.outputWorking, rAccess.outputTest);
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private ReturnValues runMethod(String name, String type, boolean accessor) throws InvocationTargetException, IllegalAccessException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream outputTest = new ByteArrayOutputStream();

        System.setOut(new PrintStream(output));
        Object r = Objects.requireNonNull(getTargetMethod(workingClass, name, type)).invoke(workingInstance, (accessor ? null : param.getListValue(workingClass)));

        System.setOut(new PrintStream(outputTest));
        Object r1 = Objects.requireNonNull(getTargetMethod(testClass, name, type)).invoke(testInstance, (accessor ? null : param.getListValue(testClass)));

        System.setOut(originalOut);

        return new ReturnValues(r, r1, output.toString(), outputTest.toString());
    }
}

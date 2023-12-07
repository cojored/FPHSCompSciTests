package com.cojored.FPHSCompSciTests;

import com.cojored.FPHSCompSciTests.params.Parameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class InputTest {
    private static List<Parameter> params;
    private static Class<?> testClass;
    private final PrintStream originalOut = System.out;
    private final Parameter param;

    public InputTest(Parameter p) {
        param = p;
    }

    public static void setParams(Parameter[] p) {
        params = Arrays.asList(p);
    }

    public static void setClass(Class<?> c) {
        testClass = c;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Parameter> data() {
        return params;
    }

    @Test
    public void test() {
        try {
            String inputString = param.getValue();
            byte[] input = inputString.getBytes(StandardCharsets.UTF_8);
            String testOut = getOutput(testClass, input);
            String out = getOutput(Thread.currentThread().getContextClassLoader().loadClass("com.cojored.FPHSCompSciTests.programs." + testClass.getName().replaceAll("_.{3}", "")), input);
            assertEquals(out, testOut);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String getOutput(Class<?> cls, byte[] in) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        System.setOut(new PrintStream(output));
        System.setIn(new ByteArrayInputStream(in));

        Method meth = cls.getMethod("main", String[].class);
        meth.invoke(null, (Object) null);
        String out = output.toString();

        System.setOut(originalOut);

        return out;
    }
}

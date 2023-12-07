package com.cojored.FPHSCompSciTests;

import com.cojored.FPHSCompSciTests.params.ContentTests;
import com.cojored.FPHSCompSciTests.params.Parameter;
import com.cojored.FPHSCompSciTests.utils.CompileJavaFile;
import com.cojored.FPHSCompSciTests.utils.JsonToParams;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Pattern;

public class TestRunner {
    public static JSONObject loadTestJson(String className) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classloader.getResourceAsStream(className + ".json")) {
            if (inputStream == null) return null;
            String data = IOUtils.toString(inputStream, String.valueOf(StandardCharsets.UTF_8));
            return new JSONObject(data);
        } catch (IOException e) {
            return null;
        }
    }


    public static void main(String[] args) {
        String className = getTestFileClassName();
        Class<?> c = getTestClass(className);

        assert className != null;
        JSONObject testJson = loadTestJson(className.replaceAll("_.{3}", ""));

        assert testJson != null;

        if (testJson.has("type") && testJson.get("type").equals("class")) {
            // Class Testing Setup
            ClassTest.setParams(JsonToParams.parse((JSONArray) (testJson.get("params"))));
            ClassTest.setClass(c);
            ClassTest.setConstructors(JsonToParams.parse((JSONArray) (testJson.get("constructors"))));
        } else {
            // Param Testing Setup
            Parameter[] arr = JsonToParams.parse((JSONArray) (testJson.get("params")));
            InputTest.setParams(arr);
            InputTest.setClass(c);
        }

        // File Content Testing Setup
        FileContentTest.setTests(new ContentTests((JSONObject) testJson.get("content")));
        FileContentTest.setFilePath(className + ".java");
        Result result = runTests(testJson.has("type") && testJson.get("type").equals("class"));

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString() + "\n");
        }

        System.out.println(className + " | Tests Run: " + result.getRunCount() + " | Tests Failed: " + result.getFailureCount() + " | Percentage Passed: " + (int) (((double) (result.getRunCount() - result.getFailureCount()) / (double) result.getRunCount()) * 100) + "%");
    }

    public static String getTestFileClassName() {
        String currentDir = System.getProperty("user.dir");
        File folder = new File(currentDir);
        for (File f : Objects.requireNonNull(folder.listFiles())) {
            if (f.isFile() && f.getName().matches("[A-Z].+.java"))
                if (loadTestJson(f.getName().replaceAll("_.{3}", "").replaceAll(".java", "")) != null && hasTestMe(f.getName()))
                    return f.getName().replace(".java", "");

        }
        return null;
    }

    public static boolean hasTestMe(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String firstLine = reader.readLine();

            Pattern pattern = Pattern.compile("\\s*//\\s*testme", Pattern.CASE_INSENSITIVE);

            return pattern.matcher(firstLine).matches();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Result runTests(boolean c) {
        if (c) return JUnitCore.runClasses(ClassTest.class, FileContentTest.class);
        else return JUnitCore.runClasses(InputTest.class, FileContentTest.class);
    }

    public static Class<?> getTestClass(String className) {
        try {
            String currentDir = System.getProperty("user.dir");

            URL classUrl = new URL("file://" + currentDir + "/");

            if (!CompileJavaFile.compileJavaFile(className + ".java"))
                throw new ClassNotFoundException("Class Not Found");

            try (URLClassLoader classLoader = new URLClassLoader(new URL[]{classUrl})) {
                return classLoader.loadClass(className);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.cojored.APCompSciTests;

import com.cojored.APCompSciTests.params.ContentTests;
import com.cojored.APCompSciTests.params.Parameter;
import com.cojored.APCompSciTests.utils.CompileJavaFile;
import com.cojored.APCompSciTests.utils.JsonToParams;
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

        // Param Testing Setup
        Parameter[] arr = JsonToParams.parse((JSONArray) (testJson.get("params")));
        InputTest.setParams(arr);
        InputTest.setClass(c);

        // File Content Testing Setup
        FileContentTest.setTests(new ContentTests((JSONObject) testJson.get("content")));
        FileContentTest.setFilePath(className + ".java");
        Result result = runTests();

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString() + "\n");
        }

        System.out.println("Tests Run: " + result.getRunCount() + " | Tests Failed: " + result.getFailureCount() + " | Percentage Passed: " + (int) (((double) (result.getRunCount() - result.getFailureCount()) / (double) result.getRunCount()) * 100) + "%");
    }

    public static String getTestFileClassName() {
        String currentDir = System.getProperty("user.dir");
        File folder = new File(currentDir);
        for (File f : Objects.requireNonNull(folder.listFiles())) {
            if (f.isFile() && f.getName().matches("[A-Z].+_.{3}.java"))
                if (loadTestJson(f.getName().replaceAll("_.{3}.java", "")) != null && hasTestMe(f.getName()))
                    return f.getName().replace(".java", "");
        }
        return null;
    }

    public static boolean hasTestMe(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the first line
            String firstLine = reader.readLine();

            // Define the pattern with case-insensitive matching and ignore spaces
            Pattern pattern = Pattern.compile("\\s*//\\s*testme", Pattern.CASE_INSENSITIVE);

            // Check if the first line matches the pattern
            return pattern.matcher(firstLine).matches();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Result runTests() {
        return JUnitCore.runClasses(InputTest.class, FileContentTest.class);
    }

    public static Class<?> getTestClass(String className) {
        try {
            String currentDir = System.getProperty("user.dir");

            // Create a URL for the directory containing YourClass.class
            URL classUrl = new URL("file://" + currentDir + "/");

            if (!CompileJavaFile.compileJavaFile(className + ".java"))
                throw new ClassNotFoundException("Class Not Found");

            // Create a new class loader with the directory in the classpath
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

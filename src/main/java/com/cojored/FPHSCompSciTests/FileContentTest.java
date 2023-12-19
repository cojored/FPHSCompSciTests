package com.cojored.FPHSCompSciTests;

import com.cojored.FPHSCompSciTests.params.ContentTests;
import com.cojored.FPHSCompSciTests.utils.CheckstyleAuditListener;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;


public class FileContentTest {
    private static ContentTests tests;
    private static String filePath;

    private static Class<?> testClass;

    public static void setTests(ContentTests t) {
        tests = t;
    }

    public static void setFilePath(String path) {
        filePath = path;
    }

    public static void setClass(Class<?> c) {
        testClass = c;
    }

    @Test
    public void hasNoAndOr() {
        assumeTrue(tests.hasNoAndOr);
        try {
            String data = new String(Files.readAllBytes(Paths.get(filePath)));
            assertFalse("You may not use the && or || symbols", data.contains("&&") || data.contains("||"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void heading() {
        assumeTrue(tests.heading);
        try {
            String data = new String(Files.readAllBytes(Paths.get(filePath)));
            assertTrue("Please ensure your heading follows the format provided in canvas", Pattern.compile("// \\*+\\d+\\*+\\r?\\n// Programmer: .*\\r?\\n// Due Date: .*\\r?\\n// Project: .*\\r?\\n// Description: (.*(\\r?\\n// ?.*)*)?\\r?\\n// \\*+\\*+").matcher(data).find());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void conditionalFormat() {
        assumeTrue(tests.conditionalFormat);
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();

            Configuration config = ConfigurationLoader.loadConfiguration("checkstyle.xml", new PropertiesExpander(System.getProperties()), ConfigurationLoader.IgnoredModulesOptions.EXECUTE);

            Checker checker = new Checker();
            CheckstyleAuditListener listener = new CheckstyleAuditListener();
            checker.setModuleClassLoader(classloader);
            checker.configure(config);
            checker.addListener(listener);

            List<File> files = Collections.singletonList(new File(filePath));

            int errors = checker.process(files);

            if (errors > 0) {
                fail(listener.errors);
            } else {
                assertTrue(true);
            }
        } catch (CheckstyleException ignored) {
        }
    }

    @Test
    public void privateVariables() {
        assumeTrue(tests.privateVariables);
        for (Field field : testClass.getDeclaredFields()) {
            assertTrue("All instance variables must be private", Modifier.isPrivate(field.getModifiers()));
        }
    }


}

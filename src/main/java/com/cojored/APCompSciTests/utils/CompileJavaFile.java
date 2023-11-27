package com.cojored.APCompSciTests.utils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class CompileJavaFile {
    public static boolean compileJavaFile(String sourceFilePath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            System.out.println("Java Compiler is not available. Make sure you are running this code with a JDK.");
            return false;
        }
        int result = compiler.run(null, null, null, sourceFilePath);

        return (result == 0);
    }
}

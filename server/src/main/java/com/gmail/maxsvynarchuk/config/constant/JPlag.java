package com.gmail.maxsvynarchuk.config.constant;

import com.gmail.maxsvynarchuk.util.ResourceManager;

import java.io.File;

public final class JPlag {
    public static final String JAVA_PATH;
    public static final String JPlAG_PATH = ResourceManager.JPLAG.getProperty("jplag.jar.path");

    static {
        String javaPathFromProperties = ResourceManager.JPLAG.getProperty("java.executable.path");
        String javaPathFromEnv = System.getenv("JAVA_HOME");

        if (!javaPathFromProperties.isBlank()) {
            JAVA_PATH = javaPathFromProperties;
        } else if (!javaPathFromEnv.isBlank()) {
            JAVA_PATH = javaPathFromEnv + File.separator + "bin" + File.separator + "java";
        } else {
            throw new IllegalStateException();
        }
    }

    private JPlag() {
    }
}

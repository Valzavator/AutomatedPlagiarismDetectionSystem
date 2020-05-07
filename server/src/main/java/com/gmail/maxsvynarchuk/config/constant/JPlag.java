package com.gmail.maxsvynarchuk.config.constant;

import com.gmail.maxsvynarchuk.util.ResourceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public final class JPlag {
    public static final String JAVA_PATH;
    public static final String JPlAG_PATH;
    public static final String BASE_CODE_DIRECTORY;

    static {
        String classpath = "";
        try {
            classpath = ResourceUtils.getFile("classpath:").getCanonicalPath();
        } catch (IOException e) {
            log.error("", e);
        }
        JPlAG_PATH = Path.of(classpath, ResourceManager.JPLAG.getProperty("jplag.jar.path")).toString();
        BASE_CODE_DIRECTORY = ResourceManager.JPLAG.getProperty("jplag.base.path");

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

package com.gmail.maxsvynarchuk.config.constant;

import com.gmail.maxsvynarchuk.util.ResourceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public final class Paths {
    public static final String REPOSITORIES_DATA_FOLDER;
    public static final String ZIP_DATA_FOLDER;
    public static final String STATIC_FOLDER;
    public static final String ANALYSIS_RESULT_FOLDER;

    static {
        String classpath = "";
        try {
            classpath = ResourceUtils.getFile("classpath:").getCanonicalPath();
        } catch (IOException e) {
            log.error("", e);
        }

        REPOSITORIES_DATA_FOLDER = Path.of(classpath, ResourceManager.PATH.getProperty("data.repositories.folder")).toString();
        ZIP_DATA_FOLDER = Path.of(classpath, ResourceManager.PATH.getProperty("data.zip.folder")).toString();
        STATIC_FOLDER = Path.of(classpath, ResourceManager.PATH.getProperty("static.folder")).toString();
        ANALYSIS_RESULT_FOLDER = Path.of(STATIC_FOLDER, ResourceManager.PATH.getProperty("data.analysis.results.folder")).toString();
    }

    private Paths() {
    }
}

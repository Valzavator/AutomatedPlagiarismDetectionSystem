package com.gmail.maxsvynarchuk.config.constant;

import com.gmail.maxsvynarchuk.util.ResourceManager;

public final class Path {
    public static final String DATA_FOLDER = ResourceManager.PATH.getProperty("data.repositories.folder");
    public static final String ANALYSIS_RESULT_FOLDER = ResourceManager.PATH.getProperty("data.analysis.results.folder");

    private Path() {
    }
}

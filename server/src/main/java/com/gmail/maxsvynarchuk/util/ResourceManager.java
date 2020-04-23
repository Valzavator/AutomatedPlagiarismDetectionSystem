package com.gmail.maxsvynarchuk.util;

import java.util.ResourceBundle;

public enum  ResourceManager {
    VCS(ResourceBundle.getBundle("properties.vcs_api")),
    PATH(ResourceBundle.getBundle("properties.path")),
    JPLAG(ResourceBundle.getBundle("properties.jplag")),
    PAGINATION(ResourceBundle.getBundle("properties.pagination")),
    DETECTION(ResourceBundle.getBundle("properties.automated_plagiarism_detection"));

    private ResourceBundle resourceBundle;

    ResourceManager(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    /**
     * Gets a string for the given key from this resource bundle
     *
     * @param key the key for the desired string
     * @return the string for the given key
     */
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    public int getPropertyAsInt(String key) {
        return Integer.parseInt(resourceBundle.getString(key));
    }
}

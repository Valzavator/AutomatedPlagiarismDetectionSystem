package com.gmail.maxsvynarchuk.service.vcs.github;

import java.util.StringJoiner;

public class TreeEntity {
    private String path;
    private String type;
    private String url;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TreeEntity.class.getSimpleName() + "[", "]")
                .add("path='" + path + "'")
                .add("type='" + type + "'")
                .add("url='" + url + "'")
                .toString();
    }
}

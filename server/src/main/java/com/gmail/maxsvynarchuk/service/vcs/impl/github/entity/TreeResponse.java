package com.gmail.maxsvynarchuk.service.vcs.impl.github.entity;

import java.util.StringJoiner;

public class TreeResponse {
    private String url;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tree.class.getSimpleName() + "[", "]")
                .add("url='" + url + "'")
                .toString();
    }
}

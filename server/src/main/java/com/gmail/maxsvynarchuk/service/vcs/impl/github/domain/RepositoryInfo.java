package com.gmail.maxsvynarchuk.service.vcs.impl.github.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.StringJoiner;

public class RepositoryInfo {

    private String name;

    @SerializedName("private")
    private boolean isPrivate;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("url")
    private String apiUrl;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryInfo that = (RepositoryInfo) o;
        return isPrivate() == that.isPrivate() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getHtmlUrl(), that.getHtmlUrl()) &&
                Objects.equals(getApiUrl(), that.getApiUrl()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), isPrivate(), getHtmlUrl(), getApiUrl(), getDescription());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RepositoryInfo.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("isPrivate=" + isPrivate)
                .add("htmlUrl='" + htmlUrl + "'")
                .add("apiUrl='" + apiUrl + "'")
                .add("description='" + description + "'")
                .toString();
    }
}

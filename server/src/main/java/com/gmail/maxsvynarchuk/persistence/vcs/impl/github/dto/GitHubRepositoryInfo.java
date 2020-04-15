package com.gmail.maxsvynarchuk.persistence.vcs.impl.github.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GitHubRepositoryInfo {
    private String name;

    @SerializedName("html_url")
    private String websiteUrl;

    @SerializedName("url")
    private String apiUrl;

    @SerializedName("private")
    private boolean isPrivate;
}

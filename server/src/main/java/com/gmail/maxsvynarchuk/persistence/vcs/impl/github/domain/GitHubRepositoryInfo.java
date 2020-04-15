package com.gmail.maxsvynarchuk.persistence.vcs.impl.github.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubRepositoryInfo {
    private String name;

    @SerializedName("html_url")
    private String websiteUrl;

    @SerializedName("url")
    private String apiUrl;

    @SerializedName("private")
    private boolean isPrivate;
}

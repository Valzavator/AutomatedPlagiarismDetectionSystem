package com.gmail.maxsvynarchuk.persistence.vcs.impl.github.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GitHubErrorResponse {
    private int status;

    private String statusText;

    private String message;

    @SerializedName("documentation_url")
    private String documentationUrl;
}

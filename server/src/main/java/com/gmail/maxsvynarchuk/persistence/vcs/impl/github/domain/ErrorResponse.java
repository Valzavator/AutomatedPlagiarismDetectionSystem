package com.gmail.maxsvynarchuk.persistence.vcs.impl.github.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int status;

    private String statusText;

    private String message;

    @SerializedName("documentation_url")
    private String documentationUrl;
}

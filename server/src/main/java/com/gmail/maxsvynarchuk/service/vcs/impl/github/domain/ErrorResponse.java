package com.gmail.maxsvynarchuk.service.vcs.impl.github.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.StringJoiner;

public class ErrorResponse {

    private int status;

    private String statusText;

    private String message;

    @SerializedName("documentation_url")
    private String documentationUrl;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getStatusText(), that.getStatusText()) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getDocumentationUrl(), that.getDocumentationUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getStatusText(), getMessage(), getDocumentationUrl());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ErrorResponse.class.getSimpleName() + "[", "]")
                .add("status='" + status + "'")
                .add("statusText='" + statusText + "'")
                .add("message='" + message + "'")
                .add("documentationUrl='" + documentationUrl + "'")
                .toString();
    }

}

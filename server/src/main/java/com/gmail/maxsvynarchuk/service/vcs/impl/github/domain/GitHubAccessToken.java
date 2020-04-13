package com.gmail.maxsvynarchuk.service.vcs.impl.github.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.StringJoiner;

public class GitHubAccessToken {

    @SerializedName("access_token")
    private String accessToken;

    private String scope;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GitHubAccessToken.class.getSimpleName() + "[", "]")
                .add("accessToken='" + accessToken + "'")
                .add("scope='" + scope + "'")
                .add("tokenType='" + tokenType + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GitHubAccessToken that = (GitHubAccessToken) o;
        return Objects.equals(getAccessToken(), that.getAccessToken()) &&
                Objects.equals(getScope(), that.getScope()) &&
                Objects.equals(getTokenType(), that.getTokenType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccessToken(), getScope(), getTokenType());
    }
}

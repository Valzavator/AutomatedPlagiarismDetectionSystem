package com.autoplag.persistence.vcs.impl.bitbucket.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BitbucketRepositoryInfo {
    private String name;

    @SerializedName("is_private")
    private boolean isPrivate;

    @Getter(AccessLevel.NONE)
    private LinksInfo links;

    public String getWebsiteUrl() {
        return links.getWebsiteUrl().getHref();
    }

    public String getApiUrl() {
        return links.getApiUrl().getHref();
    }

    public String getSourceUrl() {
        return links.getSource().getHref();
    }

    @Getter
    @ToString
    private static class LinksInfo {
        @SerializedName("html")
        private Link websiteUrl;

        @SerializedName("self")
        private Link apiUrl;

        private Link source;
    }

}

package com.autoplag.persistence.vcs.impl.bitbucket.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BitbucketBlob {
    private String path;

    private String type;

    private LinksInfo links;

    private int size;

    public String getSelfUrl() {
        return links.getSelf().getHref();
    }

    public String getMetaUrl() {
        return links.getMeta().getHref();
    }

    @Getter
    @ToString
    private static class LinksInfo {
        private Link self;

        private Link meta;
    }
}

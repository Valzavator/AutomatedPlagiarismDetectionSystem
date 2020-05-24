package com.autoplag.persistence.vcs.impl.github.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GitHubBlob {
    private String path;

    private String type;

    private String url;

    private int size;
}

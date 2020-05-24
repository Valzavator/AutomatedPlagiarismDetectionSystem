package com.autoplag.persistence.vcs.impl.github.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class GitHubTree {
    private String url;

    private List<GitHubBlob> tree;
}

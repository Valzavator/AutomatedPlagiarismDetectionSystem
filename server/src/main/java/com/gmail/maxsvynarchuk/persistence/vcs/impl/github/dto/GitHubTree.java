package com.gmail.maxsvynarchuk.persistence.vcs.impl.github.dto;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GitHubTree {
    private String url;

    private List<GitHubBlob> tree;
}

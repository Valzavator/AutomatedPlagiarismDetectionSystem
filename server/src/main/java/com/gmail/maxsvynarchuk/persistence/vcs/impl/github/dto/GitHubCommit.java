package com.gmail.maxsvynarchuk.persistence.vcs.impl.github.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GitHubCommit {
    private GitHubTree tree;
}

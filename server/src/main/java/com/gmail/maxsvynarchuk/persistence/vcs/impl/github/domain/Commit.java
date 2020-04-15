package com.gmail.maxsvynarchuk.persistence.vcs.impl.github.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commit {
    private Tree tree;
}

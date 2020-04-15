package com.gmail.maxsvynarchuk.persistence.vcs.impl.github.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tree {
    private String url;

    private List<Blob> tree;
}

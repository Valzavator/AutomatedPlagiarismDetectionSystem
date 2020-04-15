package com.gmail.maxsvynarchuk.persistence.vcs.impl.bitbucket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tree {
    private String url;

    private List<BitbucketBlob> tree;
}

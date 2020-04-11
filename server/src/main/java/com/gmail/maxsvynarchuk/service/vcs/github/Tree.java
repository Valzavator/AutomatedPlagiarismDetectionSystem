package com.gmail.maxsvynarchuk.service.vcs.github;

import java.util.List;
import java.util.StringJoiner;

public class Tree {
    List<TreeEntity> tree;

    public List<TreeEntity> getTree() {
        return tree;
    }

    public void setTree(List<TreeEntity> tree) {
        this.tree = tree;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tree.class.getSimpleName() + "[", "]")
                .add("tree=" + tree)
                .toString();
    }
}

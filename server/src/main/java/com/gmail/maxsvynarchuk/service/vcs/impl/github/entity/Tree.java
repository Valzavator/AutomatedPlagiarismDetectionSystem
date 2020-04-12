package com.gmail.maxsvynarchuk.service.vcs.impl.github.entity;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Tree {
    private String url;
    private List<TreeEntity> tree;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<TreeEntity> getTree() {
        return tree;
    }

    public void setTree(List<TreeEntity> tree) {
        this.tree = tree;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tree.class.getSimpleName() + "[", "]")
                .add("url='" + url + "'")
                .add("tree=" + tree)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree1 = (Tree) o;
        return Objects.equals(getUrl(), tree1.getUrl()) &&
                Objects.equals(getTree(), tree1.getTree());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getTree());
    }
}

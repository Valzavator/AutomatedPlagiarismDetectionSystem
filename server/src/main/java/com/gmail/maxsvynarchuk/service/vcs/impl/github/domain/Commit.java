package com.gmail.maxsvynarchuk.service.vcs.impl.github.domain;

import java.util.StringJoiner;

public class Commit {

    private Committer author;

    private Tree tree;

    public Committer getAuthor() {
        return author;
    }

    public void setAuthor(Committer author) {
        this.author = author;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Commit.class.getSimpleName() + "[", "]")
                .add("committer=" + author.toString())
                .add("tree=" + tree.toString())
                .toString();
    }

}

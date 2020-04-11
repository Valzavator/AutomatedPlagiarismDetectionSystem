package com.gmail.maxsvynarchuk.service.vcs.github;

import java.util.StringJoiner;

public class Commit {
    Committer committer;
    TreeResponse tree;

    public Committer getCommitter() {
        return committer;
    }

    public void setCommitter(Committer committer) {
        this.committer = committer;
    }

    public TreeResponse getTree() {
        return tree;
    }

    public void setTree(TreeResponse tree) {
        this.tree = tree;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Commit.class.getSimpleName() + "[", "]")
                .add("committer=" + committer.toString())
                .add("tree=" + tree.toString())
                .toString();
    }

}

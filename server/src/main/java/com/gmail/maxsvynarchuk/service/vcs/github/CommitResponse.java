package com.gmail.maxsvynarchuk.service.vcs.github;

import java.util.StringJoiner;

public class CommitResponse {
    Commit commit;

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CommitResponse.class.getSimpleName() + "[", "]")
                .add("commit=" + commit)
                .toString();
    }
}

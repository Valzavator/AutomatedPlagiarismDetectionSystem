package com.gmail.maxsvynarchuk.service.vcs;

import com.gmail.maxsvynarchuk.service.vcs.domain.Repository;
import com.gmail.maxsvynarchuk.service.vcs.impl.github.domain.Blob;
import com.gmail.maxsvynarchuk.service.vcs.impl.github.domain.Commit;
import com.gmail.maxsvynarchuk.service.vcs.impl.github.domain.RepositoryInfo;
import com.gmail.maxsvynarchuk.service.vcs.impl.github.domain.Tree;

import java.util.Date;
import java.util.Optional;

public interface VcsDownloadRepositoryService {
    RepositoryInfo downloadRepositoryInfo(String accessToken, String repositoryURL);

    Optional<Commit> downloadLastCommit(String accessToken, RepositoryInfo repositoryInfo, Date until);

    default Optional<Commit> downloadLastCommit(String accessToken, RepositoryInfo repositoryInfo) {
        return downloadLastCommit(accessToken, repositoryInfo, new Date());
    }

    Tree downloadTreeOfCommit(String accessToken, Commit commit);

    String downloadContentOfBlob(String accessToken, Blob blob);


    Repository downloadSubDirectoryFromRepository(String accessToken,
                                                  String repositoryUrl,
                                                  String prefixPath,
                                                  Date lastCommitDate);

    default Repository downloadSubDirectoryFromRepository(String accessToken,
                                                          String repositoryUrl,
                                                          String prefixPath) {
        return downloadSubDirectoryFromRepository(
                accessToken,
                repositoryUrl,
                prefixPath,
                new Date());
    }

    default Repository downloadRepository(String accessToken,
                                          String repositoryUrl,
                                          Date lastCommitDate) {
        return downloadSubDirectoryFromRepository(
                accessToken,
                repositoryUrl, "",
                lastCommitDate);
    }

    default Repository downloadRepository(String accessToken, String repositoryUrl) {
        return downloadSubDirectoryFromRepository(
                accessToken,
                repositoryUrl, "",
                new Date());
    }
}

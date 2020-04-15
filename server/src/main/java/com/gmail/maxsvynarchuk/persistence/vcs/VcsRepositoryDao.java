package com.gmail.maxsvynarchuk.persistence.vcs;

import com.gmail.maxsvynarchuk.persistence.domain.AccessToken;
import com.gmail.maxsvynarchuk.persistence.domain.RepositoryFileInfo;
import com.gmail.maxsvynarchuk.persistence.domain.RepositoryInfo;

import java.util.Date;

public interface VcsRepositoryDao {

    String getRawFileContent(AccessToken accessToken, RepositoryFileInfo fileInfo);

    RepositoryInfo getSubDirectoryRepositoryInfo(AccessToken accessToken,
                                                 String repositoryUrl,
                                                 String prefixPath,
                                                 Date lastCommitDate);

    default RepositoryInfo getSubDirectoryRepositoryInfo(AccessToken accessToken,
                                                         String repositoryUrl,
                                                         String prefixPath) {
        return getSubDirectoryRepositoryInfo(
                accessToken,
                repositoryUrl,
                prefixPath,
                new Date());
    }

    default RepositoryInfo getRepositoryInfo(AccessToken accessToken,
                                             String repositoryUrl,
                                             Date lastCommitDate) {
        return getSubDirectoryRepositoryInfo(
                accessToken,
                repositoryUrl,
                "",
                lastCommitDate);
    }

    default RepositoryInfo getRepositoryInfo(AccessToken accessToken, String repositoryUrl) {
        return getRepositoryInfo(
                accessToken,
                repositoryUrl,
                new Date());
    }
}

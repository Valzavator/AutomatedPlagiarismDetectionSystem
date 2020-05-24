package com.autoplag.persistence.vcs;

import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.persistence.domain.vcs.RepositoryFileInfo;
import com.autoplag.persistence.domain.vcs.RepositoryInfo;

import java.util.Date;

public interface VcsRepositoryDao {

    boolean checkAccess(AccessToken accessToken, String repositoryUrl);

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

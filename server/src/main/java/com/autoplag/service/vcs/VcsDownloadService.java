package com.autoplag.service.vcs;

import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.persistence.domain.vcs.RepositoryInfo;

import java.util.Date;

public interface VcsDownloadService {

    String getRootRepositoryUrl(String repositoryUrl);

    void checkAccessToRepository(AccessToken userAccessToken,
                                 String repositoryUrl);

    RepositoryInfo downloadRepository(AccessToken userAccessToken,
                                      String repositoryUrl,
                                      String prefixPath,
                                      Date lastDateCommit);

    boolean downloadAndSaveRawContentOfFiles(AccessToken userAccessToken,
                                             RepositoryInfo repositoryInfo,
                                             String repositoryDataPath);

}

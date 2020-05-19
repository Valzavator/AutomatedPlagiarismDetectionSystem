package com.gmail.maxsvynarchuk.service.vcs;

import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.RepositoryInfo;

import java.util.Date;
import java.util.Optional;

public interface VcsDownloadService {

    RepositoryInfo downloadRepository(AccessToken userAccessToken,
                                                String repositoryUrl,
                                                String prefixPath,
                                                Date lastDateCommit);

    boolean downloadAndSaveRawContentOfFiles(AccessToken userAccessToken,
                                             RepositoryInfo repositoryInfo,
                                             String repositoryDataPath);

}

package com.gmail.maxsvynarchuk.service.vcs;

import com.gmail.maxsvynarchuk.util.ResourceManager;

import java.util.Date;

public interface VCSDownloadRepositoryService {

    String DEFAULT_DATA_FOLDER = ResourceManager.VCS.getProperty("data.folder");

    void downloadRepository(String accessToken, String repositoryURL, String prefixPath, Date lastCommitDate);

    default void downloadRepository(String accessToken, String repositoryURL) {
        downloadRepository(accessToken, repositoryURL, "", null);
    }

}

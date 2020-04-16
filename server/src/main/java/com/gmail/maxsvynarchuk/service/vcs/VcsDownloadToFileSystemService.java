package com.gmail.maxsvynarchuk.service.vcs;

import com.gmail.maxsvynarchuk.persistence.domain.AccessToken;

import java.util.Date;

public interface VcsDownloadToFileSystemService {

    boolean downloadOneRepository(AccessToken userAccessToken,
                                  String repositoryUrl,
                                  String prefixPath,
                                  Date lastDateCommit);

}

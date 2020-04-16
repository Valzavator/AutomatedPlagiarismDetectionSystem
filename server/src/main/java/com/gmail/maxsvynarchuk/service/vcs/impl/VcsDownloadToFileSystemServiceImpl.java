package com.gmail.maxsvynarchuk.service.vcs.impl;

import com.gmail.maxsvynarchuk.config.constant.VCS;
import com.gmail.maxsvynarchuk.persistence.domain.AccessToken;
import com.gmail.maxsvynarchuk.persistence.domain.RepositoryFileInfo;
import com.gmail.maxsvynarchuk.persistence.domain.RepositoryInfo;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsRepositoryDao;
import com.gmail.maxsvynarchuk.service.vcs.VcsDownloadToFileSystemService;
import com.gmail.maxsvynarchuk.util.FileSystemWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class VcsDownloadToFileSystemServiceImpl implements VcsDownloadToFileSystemService {
    private final VcsRepositoryDao vcsRepositoryBitbucketDao;
    private final VcsRepositoryDao vcsRepositoryGitHubDao;
    private final FileSystemWriter fileSystemWriter;

    @Override
    public boolean downloadOneRepository(AccessToken userAccessToken,
                                         String repositoryUrl,
                                         String prefixPath,
                                         Date lastDateCommit) {
        AuthorizationProvider authorizationProvider = AuthorizationProvider.recognizeFromUrl(repositoryUrl);
        VcsRepositoryDao vcsRepositoryDao;

        if (authorizationProvider == AuthorizationProvider.GITHUB) {
            vcsRepositoryDao = vcsRepositoryGitHubDao;
        } else if (authorizationProvider == AuthorizationProvider.BITBUCKET) {
            vcsRepositoryDao = vcsRepositoryBitbucketDao;
        } else {
            return false;
        }

        RepositoryInfo repositoryInfo = vcsRepositoryDao.getSubDirectoryRepositoryInfo(
                userAccessToken,
                repositoryUrl,
                prefixPath,
                lastDateCommit);
        String directoryPath = VCS.DATA_FOLDER + "task1/" + repositoryInfo.getName() + "/";
        fileSystemWriter.deleteDirectory(directoryPath);
        for (RepositoryFileInfo fileInfo : repositoryInfo.getFilesInfo()) {
            String fileData = vcsRepositoryDao.getRawFileContent(userAccessToken, fileInfo);
            try {
                fileSystemWriter.write(
                        directoryPath + fileInfo.getPath(),
                        fileData);
            } catch (Exception ex) {
                // TODO - refactor
                ex.printStackTrace();
                return false;
            }
        }

        return true;
    }

}

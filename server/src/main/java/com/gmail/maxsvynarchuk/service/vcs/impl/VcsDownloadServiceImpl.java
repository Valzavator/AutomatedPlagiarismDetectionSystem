package com.gmail.maxsvynarchuk.service.vcs.impl;

import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.RepositoryFileInfo;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.RepositoryInfo;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.InvalidVcsUrlException;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.OAuthIllegalTokenException;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsRepositoryDao;
import com.gmail.maxsvynarchuk.service.exception.FailedToWriteToFileSystemException;
import com.gmail.maxsvynarchuk.service.vcs.VcsDownloadService;
import com.gmail.maxsvynarchuk.service.vcs.VcsOAuthService;
import com.gmail.maxsvynarchuk.util.FileSystemWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class VcsDownloadServiceImpl implements VcsDownloadService {
    public static final String ILLEGAL_AUTHORIZATION_PROVIDER = "Such authorization provider doesn't exist!";

    private final VcsRepositoryDao vcsRepositoryBitbucketDao;
    private final VcsRepositoryDao vcsRepositoryGitHubDao;
    private final VcsOAuthService vcsOAuthBitbucketService;
    private final VcsOAuthService vcsOAuthGitHubService;
    private final FileSystemWriter fileSystemWriter;

    @Override
    public void checkAccessToRepository(AccessToken userAccessToken, String repositoryUrl) {
        throw new InvalidVcsUrlException("SOME message");
    }

    @Override
    public RepositoryInfo downloadRepository(AccessToken userAccessToken,
                                             String repositoryUrl,
                                             String prefixPath,
                                             Date lastDateCommit) {
        log.debug("Attempt to download repository ({})", repositoryUrl);
        AuthorizationProvider authorizationProvider = AuthorizationProvider.recognizeFromUrl(repositoryUrl);
        if (Objects.isNull(userAccessToken) ||
                authorizationProvider != userAccessToken.getAuthorizationProvider()) {
            log.error("Invalid authorization for {} service", authorizationProvider);
            throw new OAuthIllegalTokenException("Invalid authorization for " + authorizationProvider + " service",
                    userAccessToken);
        }
        VcsRepositoryDao vcsRepositoryDao = getVcsRepositoryDaoForAuthorizationProvider(authorizationProvider);
        if (Objects.isNull(vcsRepositoryDao)) {
            log.error(ILLEGAL_AUTHORIZATION_PROVIDER);
            throw new IllegalStateException(ILLEGAL_AUTHORIZATION_PROVIDER);
        }

        try {
            RepositoryInfo repositoryInfo = vcsRepositoryDao.getSubDirectoryRepositoryInfo(
                    userAccessToken,
                    repositoryUrl,
                    prefixPath,
                    lastDateCommit);
            log.debug("Attempt to download SUCCESSFUL - repository ({}) ", repositoryUrl);
            return repositoryInfo;
        } catch (OAuthIllegalTokenException ex) {
            log.debug(ex.toString());
            AccessToken newAccessToken = tryToRefreshToken(userAccessToken);
            RepositoryInfo repositoryInfo = vcsRepositoryDao.getSubDirectoryRepositoryInfo(
                    newAccessToken,
                    repositoryUrl,
                    prefixPath,
                    lastDateCommit);
            log.debug("Attempt to download SUCCESSFUL - repository ({}) ", repositoryUrl);
            return repositoryInfo;
        }
    }

    @Override
    public boolean downloadAndSaveRawContentOfFiles(AccessToken userAccessToken,
                                                    RepositoryInfo repositoryInfo,
                                                    String repositoryDataPath) {
        log.debug("Attempt to download raw content of files - repository ({}) ",
                repositoryInfo.getWebsiteUrl());
        if (Objects.isNull(userAccessToken) ||
                repositoryInfo.getAuthorizationProvider() != userAccessToken.getAuthorizationProvider()) {
            log.error("Invalid authorization for {} service", repositoryInfo.getAuthorizationProvider());
            throw new OAuthIllegalTokenException(
                    "Invalid authorization for " + repositoryInfo.getAuthorizationProvider() + " service",
                    userAccessToken);
        }
        if (repositoryInfo.isEmptyRepository()) {
            log.debug("Attempt to download raw content of files FAILED - empty repository ({}) ",
                    repositoryInfo.getWebsiteUrl());
            return false;
        }
        VcsRepositoryDao vcsRepositoryDao = getVcsRepositoryDaoForAuthorizationProvider(
                repositoryInfo.getAuthorizationProvider());
        if (Objects.isNull(vcsRepositoryDao)) {
            log.error(ILLEGAL_AUTHORIZATION_PROVIDER);
            throw new IllegalStateException(ILLEGAL_AUTHORIZATION_PROVIDER);
        }

        fileSystemWriter.deleteDirectory(repositoryDataPath);
        for (RepositoryFileInfo fileInfo : repositoryInfo.getFilesInfo()) {
            String fileData;
            try {
                fileData = vcsRepositoryDao.getRawFileContent(userAccessToken, fileInfo);
            } catch (OAuthIllegalTokenException ex) {
                log.debug(ex.toString());
                userAccessToken = tryToRefreshToken(userAccessToken);
                fileData = vcsRepositoryDao.getRawFileContent(userAccessToken, fileInfo);
            }

            try {
                fileSystemWriter.writeStringData(
                        Path.of(repositoryDataPath, fileInfo.getPath()).toString(),
                        fileData);
            } catch (IllegalStateException ex) {
                log.error(ex.toString());
                throw new FailedToWriteToFileSystemException(ex);
            }
        }

        log.debug("Attempt to download raw content of files SUCCESSFUL - repository ({}) ",
                repositoryInfo.getWebsiteUrl());
        return true;
    }


    private VcsRepositoryDao getVcsRepositoryDaoForAuthorizationProvider(
            AuthorizationProvider authorizationProvider) {
        if (authorizationProvider == AuthorizationProvider.GITHUB) {
            return vcsRepositoryGitHubDao;
        } else if (authorizationProvider == AuthorizationProvider.BITBUCKET) {
            return vcsRepositoryBitbucketDao;
        }
        return null;
    }

    private AccessToken tryToRefreshToken(AccessToken accessToken) {
        log.debug("Attempt to refresh access token ({}) ", accessToken);
        if (AuthorizationProvider.BITBUCKET == accessToken.getAuthorizationProvider()) {
            return vcsOAuthBitbucketService.getRefreshedOAuthToken(accessToken);
        } else if (AuthorizationProvider.GITHUB == accessToken.getAuthorizationProvider()) {
            return vcsOAuthGitHubService.getRefreshedOAuthToken(accessToken);
        } else {
            throw new OAuthIllegalTokenException(accessToken);
        }
    }

}

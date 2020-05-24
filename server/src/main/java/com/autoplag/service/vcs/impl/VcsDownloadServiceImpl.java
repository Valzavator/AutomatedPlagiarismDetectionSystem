package com.autoplag.service.vcs.impl;

import com.autoplag.config.constant.VCS;
import com.autoplag.persistence.domain.type.AuthorizationProvider;
import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.persistence.domain.vcs.RepositoryFileInfo;
import com.autoplag.persistence.domain.vcs.RepositoryInfo;
import com.autoplag.persistence.exception.oauth.InvalidVcsUrlException;
import com.autoplag.persistence.exception.oauth.OAuthIllegalTokenException;
import com.autoplag.persistence.exception.oauth.VCSException;
import com.autoplag.persistence.vcs.VcsRepositoryDao;
import com.autoplag.service.exception.FailedToWriteToFileSystemException;
import com.autoplag.service.vcs.VcsDownloadService;
import com.autoplag.service.vcs.VcsOAuthService;
import com.autoplag.util.FileSystemWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final Pattern rootRepositoryPattern = Pattern.compile("(" + VCS.BITBUCKET_WEBSITE_REPOSITORY_PREFIX_ENDPOINT + "(/[^\\s/]+){2}|" +
            VCS.GITHUB_WEBSITE_REPOSITORY_PREFIX_ENDPOINT + "(/[^\\s/]+){2})");

    @Override
    public String getRootRepositoryUrl(String repositoryUrl) {
        Matcher matcher = rootRepositoryPattern.matcher(repositoryUrl);
        if (!matcher.find()) {
            throw new InvalidVcsUrlException("Invalid url for VCS services (GitHub or Bitbucket)");
        }
        return matcher.group();
    }

    @Override
    public void checkAccessToRepository(AccessToken userAccessToken, String repositoryUrl) {
        log.debug("Check access to repository ({})", repositoryUrl);
        AuthorizationProvider authorizationProvider = AuthorizationProvider.recognizeFromUrl(repositoryUrl);
        try {
            checkAccessToken(userAccessToken, authorizationProvider);
            VcsRepositoryDao vcsRepositoryDao = getVcsRepositoryDaoForAuthorizationProvider(authorizationProvider);
            vcsRepositoryDao.checkAccess(userAccessToken, repositoryUrl);
        } catch (VCSException ex) {
            log.debug(ex.toString());
            throw new VCSException("Репозиторій не існує або Ви не отримали доступ до нього!", ex);
        }
    }

    @Override
    public RepositoryInfo downloadRepository(AccessToken userAccessToken,
                                             String repositoryUrl,
                                             String prefixPath,
                                             Date lastDateCommit) {
        log.debug("Attempt to download repository ({})", repositoryUrl);
        AuthorizationProvider authorizationProvider = AuthorizationProvider.recognizeFromUrl(repositoryUrl);
        checkAccessToken(userAccessToken, authorizationProvider);
        VcsRepositoryDao vcsRepositoryDao = getVcsRepositoryDaoForAuthorizationProvider(authorizationProvider);

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
        checkAccessToken(userAccessToken, repositoryInfo.getAuthorizationProvider());
        if (repositoryInfo.isEmptyRepository()) {
            log.debug("Attempt to download raw content of files FAILED - empty repository ({}) ",
                    repositoryInfo.getWebsiteUrl());
            return false;
        }
        VcsRepositoryDao vcsRepositoryDao = getVcsRepositoryDaoForAuthorizationProvider(
                repositoryInfo.getAuthorizationProvider());

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
        log.error(ILLEGAL_AUTHORIZATION_PROVIDER);
        throw new IllegalStateException(ILLEGAL_AUTHORIZATION_PROVIDER);
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

    private void checkAccessToken(AccessToken accessToken, AuthorizationProvider authorizationProvider) {
        if (Objects.isNull(accessToken) ||
                authorizationProvider != accessToken.getAuthorizationProvider()) {
            log.error("Invalid authorization for {} service", authorizationProvider);
            throw new OAuthIllegalTokenException("Invalid authorization for " + authorizationProvider + " service",
                    accessToken);
        }
    }

}

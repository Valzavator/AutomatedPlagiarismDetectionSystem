package com.gmail.maxsvynarchuk.service.vcs.impl;

import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.RepositoryFileInfo;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.RepositoryInfo;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.InvalidVcsUrlException;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.OAuthIllegalTokenException;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsRepositoryDao;
import com.gmail.maxsvynarchuk.service.vcs.VcsDownloadToFileSystemService;
import com.gmail.maxsvynarchuk.service.vcs.VcsOAuthService;
import com.gmail.maxsvynarchuk.util.FileSystemWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class VcsDownloadToFileSystemServiceImpl implements VcsDownloadToFileSystemService {
    private final VcsRepositoryDao vcsRepositoryBitbucketDao;
    private final VcsRepositoryDao vcsRepositoryGitHubDao;
    private final VcsOAuthService vcsOAuthBitbucketService;
    private final VcsOAuthService vcsOAuthGitHubService;
    private final FileSystemWriter fileSystemWriter;

    @Transactional
    @Override
    public boolean downloadOneRepository(AccessToken userAccessToken,
                                         String repositoryUrl,
                                         String prefixPath,
                                         Date lastDateCommit,
                                         String repositoryDataPath) {
        log.debug("Attempt to download repository ({})", repositoryUrl);
        AuthorizationProvider authorizationProvider = AuthorizationProvider.recognizeFromUrl(repositoryUrl);

        if (Objects.isNull(userAccessToken) ||
                authorizationProvider != userAccessToken.getAuthorizationProvider()) {
            log.debug("Access token is not suitable for this authorization provider!");
            return false;
        }

        VcsRepositoryDao vcsRepositoryDao = getVcsRepositoryDaoForAuthorizationProvider(authorizationProvider);
        if (Objects.isNull(vcsRepositoryDao)) {
            log.debug("Such authorization provider doesn't exist!");
            return false;
        }

        RepositoryInfo repositoryInfo;
        try {
            repositoryInfo = vcsRepositoryDao.getSubDirectoryRepositoryInfo(
                    userAccessToken,
                    repositoryUrl,
                    prefixPath,
                    lastDateCommit);
        } catch (InvalidVcsUrlException ex) {
            // TODO refactor exception handler
            log.error(ex.toString());
            return false;
        } catch (OAuthIllegalTokenException ex) {
            // TODO implement handler for user info about invalid token
            log.error(ex.toString());
            AccessToken newAccessToken = tryToRefreshToken(userAccessToken);
            return downloadOneRepository(newAccessToken,
                    repositoryUrl,
                    prefixPath,
                    lastDateCommit,
                    repositoryDataPath);
        }

        if (repositoryInfo.isEmptyRepository()) {
            log.debug("Attempt to download FAILED - empty repository ({}) ", repositoryUrl);
            return false;
        }

        fileSystemWriter.deleteDirectory(repositoryDataPath);
        for (RepositoryFileInfo fileInfo : repositoryInfo.getFilesInfo()) {
            String fileData = vcsRepositoryDao.getRawFileContent(userAccessToken, fileInfo);
            try {
                fileSystemWriter.write(
                        repositoryDataPath + fileInfo.getPath(),
                        fileData);
            } catch (Exception ex) {
                // TODO refactor exception handler
                log.error(ex.toString());
                return false;
            }
        }

        log.debug("Attempt to download SUCCESSFUL - repository ({}) ", repositoryUrl);
        return true;
    }

    private VcsRepositoryDao getVcsRepositoryDaoForAuthorizationProvider(AuthorizationProvider authorizationProvider) {
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

package com.gmail.maxsvynarchuk.service.vcs.impl.github;

import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsOAuthDao;
import com.gmail.maxsvynarchuk.service.vcs.VcsOAuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("vcsOAuthGitHubService")
public class VcsOAuthGitHubService implements VcsOAuthService {
    private final VcsOAuthDao vcsAuthorizingOAuthDao;

    public VcsOAuthGitHubService(
            @Qualifier("vcsOAuthGitHubDao") VcsOAuthDao vcsAuthorizingOAuthDao) {
        this.vcsAuthorizingOAuthDao = vcsAuthorizingOAuthDao;
    }

    @Override
    public String getAuthorizeOAuthUrl() {
        return vcsAuthorizingOAuthDao.getAuthorizeOAuthUrl();
    }

    @Override
    public AccessToken getAuthorizeOAuthToken(String code, String returnedState) {
        return vcsAuthorizingOAuthDao.getAuthorizeOAuthToken(code, returnedState);
    }

    @Override
    public AccessToken getRefreshedOAuthToken(AccessToken expiredAccessToken) {
        return vcsAuthorizingOAuthDao.getRefreshedOAuthToken(expiredAccessToken);
    }
}

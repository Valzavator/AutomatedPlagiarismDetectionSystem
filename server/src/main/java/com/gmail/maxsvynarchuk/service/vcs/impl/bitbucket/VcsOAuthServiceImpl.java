package com.gmail.maxsvynarchuk.service.vcs.impl.bitbucket;

import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsOAuthDao;
import com.gmail.maxsvynarchuk.service.vcs.VcsOAuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("vcsOAuthBitbucketService")
public class VcsOAuthServiceImpl implements VcsOAuthService {
    private final VcsOAuthDao vcsAuthorizingOAuthDao;

    public VcsOAuthServiceImpl(
            @Qualifier("vcsOAuthBitbucketDao") VcsOAuthDao vcsAuthorizingOAuthDao) {
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

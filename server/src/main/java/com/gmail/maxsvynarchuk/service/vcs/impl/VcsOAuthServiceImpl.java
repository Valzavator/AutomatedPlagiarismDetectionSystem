package com.gmail.maxsvynarchuk.service.vcs.impl;

import com.gmail.maxsvynarchuk.persistence.dao.AccessTokenDao;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsOAuthDao;
import com.gmail.maxsvynarchuk.service.vcs.VcsOAuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public abstract class VcsOAuthServiceImpl implements VcsOAuthService {
    private final VcsOAuthDao vcsAuthorizingOAuthDao;
    private final AccessTokenDao accessTokenDao;

    @Override
    public String getAuthorizeOAuthUrl() {
        return vcsAuthorizingOAuthDao.getAuthorizeOAuthUrl();
    }

    @Override
    public AccessToken getAuthorizeOAuthToken(String code, String returnedState) {
        return vcsAuthorizingOAuthDao.getAuthorizeOAuthToken(code, returnedState);
    }

    @Transactional
    @Override
    public AccessToken getRefreshedOAuthToken(AccessToken expiredAccessToken) {
        AccessToken newAccessToken =
                vcsAuthorizingOAuthDao.getRefreshedOAuthToken(expiredAccessToken);
        newAccessToken.setId(expiredAccessToken.getId());
        newAccessToken.setUser(expiredAccessToken.getUser());
        accessTokenDao.save(newAccessToken);
        return newAccessToken;
    }

}

package com.autoplag.service.vcs.impl;

import com.autoplag.persistence.dao.AccessTokenDao;
import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.persistence.vcs.VcsOAuthDao;
import com.autoplag.service.vcs.VcsOAuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public abstract class VcsOAuthServiceImpl implements VcsOAuthService {
    protected final VcsOAuthDao vcsAuthorizingOAuthDao;
    protected final AccessTokenDao accessTokenDao;

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

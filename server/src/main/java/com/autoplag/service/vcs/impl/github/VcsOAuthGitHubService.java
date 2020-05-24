package com.autoplag.service.vcs.impl.github;

import com.autoplag.config.constant.VCS;
import com.autoplag.persistence.dao.AccessTokenDao;
import com.autoplag.persistence.vcs.VcsOAuthDao;
import com.autoplag.service.vcs.impl.VcsOAuthServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("vcsOAuthGitHubService")
public class VcsOAuthGitHubService extends VcsOAuthServiceImpl {

    public VcsOAuthGitHubService(
            @Qualifier("vcsOAuthGitHubDao") VcsOAuthDao vcsAuthorizingOAuthDao,
            AccessTokenDao accessTokenDao) {
        super(vcsAuthorizingOAuthDao, accessTokenDao);
    }

    @Override
    public String getAuthorizeOAuthUrl(Long userId) {
        String redirectUrl = VCS.GITHUB_AUTHORIZE_OAUTH_CALLBACK_URL + "/" + userId;
        return vcsAuthorizingOAuthDao.getAuthorizeOAuthUrl(redirectUrl);
    }

}

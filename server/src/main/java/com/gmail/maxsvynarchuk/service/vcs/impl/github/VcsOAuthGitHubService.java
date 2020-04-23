package com.gmail.maxsvynarchuk.service.vcs.impl.github;

import com.gmail.maxsvynarchuk.persistence.dao.AccessTokenDao;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsOAuthDao;
import com.gmail.maxsvynarchuk.service.vcs.VcsOAuthService;
import com.gmail.maxsvynarchuk.service.vcs.impl.VcsOAuthServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("vcsOAuthGitHubService")
public class VcsOAuthGitHubService extends VcsOAuthServiceImpl {

    public VcsOAuthGitHubService(
            @Qualifier("vcsOAuthGitHubDao") VcsOAuthDao vcsAuthorizingOAuthDao,
            AccessTokenDao accessTokenDao) {
        super(vcsAuthorizingOAuthDao, accessTokenDao);
    }

}

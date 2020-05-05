package com.gmail.maxsvynarchuk.service.vcs.impl.bitbucket;

import com.gmail.maxsvynarchuk.config.constant.VCS;
import com.gmail.maxsvynarchuk.persistence.dao.AccessTokenDao;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsOAuthDao;
import com.gmail.maxsvynarchuk.service.vcs.impl.VcsOAuthServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("vcsOAuthBitbucketService")
public class VcsOAuthBitbucketService extends VcsOAuthServiceImpl {

    public VcsOAuthBitbucketService(
            @Qualifier("vcsOAuthBitbucketDao") VcsOAuthDao vcsAuthorizingOAuthDao,
            AccessTokenDao accessTokenDao) {
        super(vcsAuthorizingOAuthDao, accessTokenDao);
    }

    @Override
    public String getAuthorizeOAuthUrl(Long userId) {
        String redirectUrl = VCS.BITBUCKET_AUTHORIZE_OAUTH_CALLBACK_URL + "/" + userId;
        return vcsAuthorizingOAuthDao.getAuthorizeOAuthUrl(redirectUrl);
    }

}

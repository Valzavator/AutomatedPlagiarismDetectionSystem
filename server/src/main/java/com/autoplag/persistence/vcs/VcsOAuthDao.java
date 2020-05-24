package com.autoplag.persistence.vcs;

import com.autoplag.persistence.domain.vcs.AccessToken;

public interface VcsOAuthDao {

    String getAuthorizeOAuthUrl(String redirectUrl);

    default String getAuthorizeOAuthUrl() {
        return getAuthorizeOAuthUrl("");
    }

    AccessToken getAuthorizeOAuthToken(String code, String returnedState);

    AccessToken getRefreshedOAuthToken(AccessToken expiredAccessToken);

}

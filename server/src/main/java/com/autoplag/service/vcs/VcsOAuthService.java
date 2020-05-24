package com.autoplag.service.vcs;

import com.autoplag.persistence.domain.vcs.AccessToken;

public interface VcsOAuthService {

    String getAuthorizeOAuthUrl(Long userId);

    AccessToken getAuthorizeOAuthToken(String code, String returnedState);

    AccessToken getRefreshedOAuthToken(AccessToken expiredAccessToken);

}

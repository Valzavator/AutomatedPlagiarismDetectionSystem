package com.gmail.maxsvynarchuk.service.vcs;

import com.gmail.maxsvynarchuk.persistence.domain.AccessToken;

public interface VcsOAuthService {

    String getAuthorizeOAuthUrl();

    AccessToken getAuthorizeOAuthToken(String code, String returnedState);

    AccessToken getRefreshedOAuthToken(AccessToken expiredAccessToken);

}
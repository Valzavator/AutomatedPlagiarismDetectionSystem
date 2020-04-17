package com.gmail.maxsvynarchuk.persistence.vcs;

import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;

public interface VcsOAuthDao {

    String getAuthorizeOAuthUrl();

    AccessToken getAuthorizeOAuthToken(String code, String returnedState);

    AccessToken getRefreshedOAuthToken(AccessToken expiredAccessToken);

}

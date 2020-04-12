package com.gmail.maxsvynarchuk.service.vcs;

public interface VCSAuthorizingOAuthService {

    String getAuthorizeOAuthURL(String state);

    String getAuthorizeOAuthToken(String code, String validState, String returnedState);

}

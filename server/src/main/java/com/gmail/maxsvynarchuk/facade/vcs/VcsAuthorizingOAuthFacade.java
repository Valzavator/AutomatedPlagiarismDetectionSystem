package com.gmail.maxsvynarchuk.facade.vcs;

public interface VcsAuthorizingOAuthFacade {

    String getAuthorizeOAuthURL(String user);

    String getAuthorizeOAuthToken(String code, String user, String returnedState);

}

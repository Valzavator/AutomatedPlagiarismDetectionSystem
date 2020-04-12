package com.gmail.maxsvynarchuk.facade.vcs.impl.github;

import com.gmail.maxsvynarchuk.facade.vcs.VCSAuthorizingOAuthFacade;
import com.gmail.maxsvynarchuk.service.vcs.VCSAuthorizingOAuthService;
import org.springframework.stereotype.Service;

@Service
public class VCSAuthorizingOAuthFacadeImpl implements VCSAuthorizingOAuthFacade {
    private final VCSAuthorizingOAuthService vcsAuthorizingOAuthService;

    public VCSAuthorizingOAuthFacadeImpl(VCSAuthorizingOAuthService vcsAuthorizingOAuthService) {
        this.vcsAuthorizingOAuthService = vcsAuthorizingOAuthService;
    }

    @Override
    public String getAuthorizeOAuthURL(String user) {
        return vcsAuthorizingOAuthService.getAuthorizeOAuthURL(user);
    }

    @Override
    public String getAuthorizeOAuthToken(String code, String user, String returnedState) {
        return vcsAuthorizingOAuthService.getAuthorizeOAuthToken(code, user, returnedState);
    }

}

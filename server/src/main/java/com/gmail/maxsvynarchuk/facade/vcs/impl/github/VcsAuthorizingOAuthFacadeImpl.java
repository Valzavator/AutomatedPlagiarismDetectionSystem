package com.gmail.maxsvynarchuk.facade.vcs.impl.github;

import com.gmail.maxsvynarchuk.facade.vcs.VcsAuthorizingOAuthFacade;
import com.gmail.maxsvynarchuk.service.vcs.VcsAuthorizingOAuthService;
import org.springframework.stereotype.Service;

@Service
public class VcsAuthorizingOAuthFacadeImpl implements VcsAuthorizingOAuthFacade {
    private final VcsAuthorizingOAuthService vcsAuthorizingOAuthService;

    public VcsAuthorizingOAuthFacadeImpl(VcsAuthorizingOAuthService vcsAuthorizingOAuthService) {
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

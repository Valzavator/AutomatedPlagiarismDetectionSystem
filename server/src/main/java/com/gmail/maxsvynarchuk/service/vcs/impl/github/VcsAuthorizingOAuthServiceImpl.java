package com.gmail.maxsvynarchuk.service.vcs.impl.github;

import com.gmail.maxsvynarchuk.config.constant.VCS;
import com.gmail.maxsvynarchuk.service.exception.OAuthIllegalAuthorizeStateException;
import com.gmail.maxsvynarchuk.service.exception.OAuthIllegalTokenException;
import com.gmail.maxsvynarchuk.service.exception.OAuthIllegalTokenScopeException;
import com.gmail.maxsvynarchuk.service.vcs.VcsAuthorizingOAuthService;
import com.gmail.maxsvynarchuk.service.vcs.impl.github.domain.GitHubAccessToken;
import kong.unirest.Unirest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VcsAuthorizingOAuthServiceImpl implements VcsAuthorizingOAuthService {
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String SCOPE = "scope";
    private static final String STATE = "state";
    private static final String CODE = "code";

    @Override
    public String getAuthorizeOAuthURL(String state) {
        return VCS.GITHUB_AUTHORIZE_OAUTH_URL +
                "?" + CLIENT_ID + "=" + VCS.GITHUB_AUTHORIZE_OAUTH_CLIENT_ID +
                "&" + SCOPE + "=" + VCS.GITHUB_AUTHORIZE_OAUTH_SCOPE +
                "&" + STATE + "=" + state;
    }

    @Override
    public String getAuthorizeOAuthToken(String code, String validState, String returnedState) {
        if (!validState.equals(returnedState)) {
            throw new OAuthIllegalAuthorizeStateException();
        }

        GitHubAccessToken accessToken = Unirest.post(VCS.GITHUB_AUTHORIZE_OAUTH_TOKEN_URL)
                .queryString(CLIENT_ID, VCS.GITHUB_AUTHORIZE_OAUTH_CLIENT_ID)
                .queryString(CLIENT_SECRET, VCS.GITHUB_AUTHORIZE_OAUTH_CLIENT_SECRET)
                .queryString(CODE, code)
                .queryString(STATE, validState)
                .header("Accept", "application/json")
                .asObject(GitHubAccessToken.class)
                .getBody();

        validateAccessToken(accessToken);

        return accessToken.getTokenType() + " " + accessToken.getAccessToken();
    }

    private void validateAccessToken(GitHubAccessToken accessToken) {
        if (Objects.isNull(accessToken) ||
                Objects.isNull(accessToken.getAccessToken()) ||
                Objects.isNull(accessToken.getTokenType())) {
            throw new OAuthIllegalTokenException();
        }

        if (Objects.isNull(accessToken.getScope()) ||
                !accessToken.getScope().equals(VCS.GITHUB_AUTHORIZE_OAUTH_SCOPE)) {
            throw new OAuthIllegalTokenScopeException();
        }
    }

}

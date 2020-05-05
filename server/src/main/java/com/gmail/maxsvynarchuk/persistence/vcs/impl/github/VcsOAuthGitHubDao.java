package com.gmail.maxsvynarchuk.persistence.vcs.impl.github;

import com.gmail.maxsvynarchuk.config.constant.VCS;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.InvalidVcsUrlException;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.OAuthIllegalAuthorizeStateException;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.OAuthIllegalTokenException;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.OAuthIllegalTokenScopeException;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsOAuthDao;
import kong.unirest.Unirest;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository("vcsOAuthGitHubDao")
public class VcsOAuthGitHubDao implements VcsOAuthDao {
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String SCOPE = "scope";
    private static final String STATE = "state";
    private static final String CODE = "code";
    private static final String REDIRECT_URI = "redirect_uri";

    @Override
    public String getAuthorizeOAuthUrl(String redirectUrl) {
        String redirectParameter = null;
        if (Objects.nonNull(redirectUrl) && redirectUrl.length() > 0) {
            if (!redirectUrl.startsWith(VCS.GITHUB_AUTHORIZE_OAUTH_CALLBACK_URL)) {
                throw new InvalidVcsUrlException(redirectUrl);
            }
            redirectParameter = REDIRECT_URI + "=" + redirectUrl;
        }

        return VCS.GITHUB_AUTHORIZE_OAUTH_URL +
                "?" + CLIENT_ID + "=" + VCS.GITHUB_AUTHORIZE_OAUTH_CLIENT_ID +
                "&" + SCOPE + "=" + VCS.GITHUB_AUTHORIZE_OAUTH_SCOPE +
                "&" + STATE + "=" + VCS.GITHUB_AUTHORIZE_OAUTH_STATE +
                (Objects.nonNull(redirectParameter) ? "&" + redirectParameter : "");
    }

    @Override
    public AccessToken getAuthorizeOAuthToken(String code, String returnedState) {
        if (!VCS.GITHUB_AUTHORIZE_OAUTH_STATE.equals(returnedState)) {
            throw new OAuthIllegalAuthorizeStateException();
        }

        AccessToken accessToken = Unirest.post(VCS.GITHUB_AUTHORIZE_OAUTH_TOKEN_URL)
                .queryString(CLIENT_ID, VCS.GITHUB_AUTHORIZE_OAUTH_CLIENT_ID)
                .queryString(CLIENT_SECRET, VCS.GITHUB_AUTHORIZE_OAUTH_CLIENT_SECRET)
                .queryString(CODE, code)
                .queryString(STATE, VCS.GITHUB_AUTHORIZE_OAUTH_STATE)
                .header("Accept", "application/json")
                .asObject(AccessToken.class)
                .getBody();
        accessToken.setAuthorizationProvider(AuthorizationProvider.GITHUB);

        validateAccessToken(accessToken);

        return accessToken;
    }

    /**
     * @param expiredAccessToken OAuth2 token for GitHub
     * @throws OAuthIllegalTokenException invalid token initially,
     *                                    GitHub hasn't time limit for oauth tokens
     */
    @Override
    public AccessToken getRefreshedOAuthToken(AccessToken expiredAccessToken) {
        throw new OAuthIllegalTokenException(expiredAccessToken);
    }

    private void validateAccessToken(AccessToken accessToken) {
        if (Objects.isNull(accessToken) ||
                Objects.isNull(accessToken.getAccessToken()) ||
                Objects.isNull(accessToken.getTokenType())) {
            throw new OAuthIllegalTokenException(accessToken);
        }

        if (Objects.isNull(accessToken.getScope()) ||
                !accessToken.getScope().equals(VCS.GITHUB_AUTHORIZE_OAUTH_SCOPE)) {
            throw new OAuthIllegalTokenScopeException(accessToken);
        }
    }
}

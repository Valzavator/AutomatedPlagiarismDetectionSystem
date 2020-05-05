package com.gmail.maxsvynarchuk.persistence.vcs.impl.bitbucket;

import com.gmail.maxsvynarchuk.config.constant.VCS;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.InvalidVcsUrlException;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.OAuthIllegalTokenException;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.OAuthIllegalTokenScopeException;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsOAuthDao;
import kong.unirest.Unirest;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository("vcsOAuthBitbucketDao")
public class VcsOAuthBitbucketDao implements VcsOAuthDao {
    private static final String CLIENT_ID = "client_id";
    private static final String RESPONSE_TYPE_PARAM = "response_type=code";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String GRANT_TYPE = "grant_type";
    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String CODE = "code";
    public static final String REDIRECT_URI = "redirect_uri";

    @Override
    public String getAuthorizeOAuthUrl(String redirectUrl) {
        String redirectParameter = null;
        if (Objects.nonNull(redirectUrl) && redirectUrl.length() > 0) {
            if (!redirectUrl.startsWith(VCS.BITBUCKET_AUTHORIZE_OAUTH_CALLBACK_URL)) {
                throw new InvalidVcsUrlException(redirectUrl);
            }
            redirectParameter = REDIRECT_URI + "=" + redirectUrl;
        }

        return VCS.BITBUCKET_AUTHORIZE_OAUTH_URL +
                "?" + CLIENT_ID + "=" + VCS.BITBUCKET_AUTHORIZE_OAUTH_CLIENT_ID +
                "&" + RESPONSE_TYPE_PARAM +
                (Objects.nonNull(redirectParameter) ? "&" + redirectParameter : "");
    }

    @Override
    public AccessToken getAuthorizeOAuthToken(String code, String returnedState) {
        AccessToken accessToken = Unirest.post(VCS.BITBUCKET_AUTHORIZE_OAUTH_TOKEN_URL)
                .field(CLIENT_ID, VCS.BITBUCKET_AUTHORIZE_OAUTH_CLIENT_ID)
                .field(CLIENT_SECRET, VCS.BITBUCKET_AUTHORIZE_OAUTH_CLIENT_SECRET)
                .field(GRANT_TYPE, AUTHORIZATION_CODE)
                .field(CODE, code)
                .field(REDIRECT_URI, returnedState)
                .asObject(AccessToken.class)
                .getBody();
        return processAccessToken(accessToken);
    }

    @Override
    public AccessToken getRefreshedOAuthToken(AccessToken expiredAccessToken) {
        AccessToken accessToken = Unirest.post(VCS.BITBUCKET_AUTHORIZE_OAUTH_TOKEN_URL)
                .field(CLIENT_ID, VCS.BITBUCKET_AUTHORIZE_OAUTH_CLIENT_ID)
                .field(CLIENT_SECRET, VCS.BITBUCKET_AUTHORIZE_OAUTH_CLIENT_SECRET)
                .field(GRANT_TYPE, REFRESH_TOKEN)
                .field(REFRESH_TOKEN, expiredAccessToken.getRefreshToken())
                .asObject(AccessToken.class)
                .getBody();
        return processAccessToken(accessToken);
    }

    private AccessToken processAccessToken(AccessToken accessToken) {
        accessToken.setAuthorizationProvider(AuthorizationProvider.BITBUCKET);
        validateAccessToken(accessToken);

        // TODO - refactor: capitalize first letter in another way
        accessToken.setTokenType(
                capitalizeFirstLetter(accessToken.getTokenType()));
        return accessToken;
    }

    private void validateAccessToken(AccessToken accessToken) {
        if (Objects.isNull(accessToken) ||
                Objects.isNull(accessToken.getAccessToken()) ||
                Objects.isNull(accessToken.getTokenType())) {
            throw new OAuthIllegalTokenException(accessToken);
        }

        if (Objects.isNull(accessToken.getScope()) ||
                !accessToken.getScope().equals(VCS.BITBUCKET_AUTHORIZE_OAUTH_SCOPE)) {
            throw new OAuthIllegalTokenScopeException(accessToken);
        }
    }

    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

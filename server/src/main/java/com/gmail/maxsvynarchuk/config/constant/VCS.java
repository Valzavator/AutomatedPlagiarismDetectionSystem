package com.gmail.maxsvynarchuk.config.constant;

import com.gmail.maxsvynarchuk.util.ResourceManager;

public final class VCS {
    public static final String GITHUB_AUTHORIZE_OAUTH_CLIENT_ID = System.getenv("CLIENT_ID");
    public static final String GITHUB_AUTHORIZE_OAUTH_CLIENT_SECRET = System.getenv("CLIENT_SECRET");
    public static final String GITHUB_AUTHORIZE_OAUTH_URL = ResourceManager.VCS.getProperty("github.authorize.oauth.url");
    public static final String GITHUB_AUTHORIZE_OAUTH_SCOPE = ResourceManager.VCS.getProperty("github.authorize.oauth.scope");
//    public static final String GITHUB_AUTHORIZE_OAUTH_STATE = ResourceManager.VCS.getProperty("github.authorize.oauth.state");
    public static final String GITHUB_AUTHORIZE_OAUTH_TOKEN_URL = ResourceManager.VCS.getProperty("github.authorize.oauth.token.url");


    private VCS() {
    }
}

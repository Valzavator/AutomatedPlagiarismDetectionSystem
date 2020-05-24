package com.autoplag.config.constant;

import com.autoplag.util.ResourceManager;

public final class VCS {
    public static final String GITHUB_AUTHORIZE_OAUTH_CLIENT_ID = System.getenv("GITHUB_CLIENT_ID");
    public static final String GITHUB_AUTHORIZE_OAUTH_CLIENT_SECRET = System.getenv("GITHUB_CLIENT_SECRET");
    public static final String GITHUB_AUTHORIZE_OAUTH_URL = ResourceManager.VCS.getProperty("github.authorize.oauth.url");
    public static final String GITHUB_AUTHORIZE_OAUTH_TOKEN_URL = ResourceManager.VCS.getProperty("github.authorize.oauth.token.url");
    public static final String GITHUB_AUTHORIZE_OAUTH_CALLBACK_URL = ResourceManager.VCS.getProperty("github.authorize.oauth.callback.url");
    public static final String GITHUB_AUTHORIZE_OAUTH_SCOPE = ResourceManager.VCS.getProperty("github.authorize.oauth.scope");
    public static final String GITHUB_AUTHORIZE_OAUTH_STATE = ResourceManager.VCS.getProperty("github.authorize.oauth.state");
    public static final String GITHUB_WEBSITE_REPOSITORY_PREFIX_ENDPOINT = ResourceManager.VCS.getProperty("github.website.repository.prefix.endpoint");
    public static final String GITHUB_API_REPOSITORY_PREFIX_ENDPOINT = ResourceManager.VCS.getProperty("github.api.repository.prefix.endpoint");
    public static final String GITHUB_API_COMMITS_SUFFIX_ENDPOINT = ResourceManager.VCS.getProperty("github.api.commits.suffix.endpoint");
    public static final String GITHUB_API_JSON_ACCEPT_FORMAT = ResourceManager.VCS.getProperty("github.api.json.accept.format");
    public static final String GITHUB_API_RAW_ACCEPT_FORMAT = ResourceManager.VCS.getProperty("github.api.raw.accept.format");

    public static final String BITBUCKET_AUTHORIZE_OAUTH_CLIENT_ID = System.getenv("BITBUCKET_CLIENT_ID");
    public static final String BITBUCKET_AUTHORIZE_OAUTH_CLIENT_SECRET = System.getenv("BITBUCKET_CLIENT_SECRET");
    public static final String BITBUCKET_AUTHORIZE_OAUTH_URL = ResourceManager.VCS.getProperty("bitbucket.authorize.oauth.url");
    public static final String BITBUCKET_AUTHORIZE_OAUTH_CALLBACK_URL = ResourceManager.VCS.getProperty("bitbucket.authorize.oauth.callback.url");
    public static final String BITBUCKET_AUTHORIZE_OAUTH_TOKEN_URL = ResourceManager.VCS.getProperty("bitbucket.authorize.oauth.token.url");
    public static final String BITBUCKET_AUTHORIZE_OAUTH_SCOPE = ResourceManager.VCS.getProperty("bitbucket.authorize.oauth.scope");
    public static final String BITBUCKET_WEBSITE_REPOSITORY_PREFIX_ENDPOINT = ResourceManager.VCS.getProperty("bitbucket.website.repository.prefix.endpoint");
    public static final String BITBUCKET_API_REPOSITORY_PREFIX_ENDPOINT = ResourceManager.VCS.getProperty("bitbucket.api.repository.prefix.endpoint");

    private VCS() {
    }
}

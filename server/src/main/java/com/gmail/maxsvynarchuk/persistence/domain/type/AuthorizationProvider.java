package com.gmail.maxsvynarchuk.persistence.domain.type;

import com.gmail.maxsvynarchuk.config.constant.VCS;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.InvalidVcsUrlException;

import java.util.Objects;

public enum AuthorizationProvider {
    GITHUB(VCS.GITHUB_WEBSITE_REPOSITORY_PREFIX_ENDPOINT,
            VCS.GITHUB_API_REPOSITORY_PREFIX_ENDPOINT),
    BITBUCKET(VCS.BITBUCKET_WEBSITE_REPOSITORY_PREFIX_ENDPOINT,
            VCS.BITBUCKET_API_REPOSITORY_PREFIX_ENDPOINT);

    private final String websiteRepoPrefix;
    private final String apiRepoPrefix;

    AuthorizationProvider(String websiteRepoPrefix, String apiRepoPrefix) {
        this.websiteRepoPrefix = websiteRepoPrefix;
        this.apiRepoPrefix = apiRepoPrefix;
    }

    public String getApiRepositoryUrl(String repositoryUrl) {
        if (Objects.isNull(repositoryUrl) || repositoryUrl.isBlank()) {
            throw new InvalidVcsUrlException(repositoryUrl);
        }

        if (repositoryUrl.startsWith(apiRepoPrefix)) {
            return repositoryUrl;
        } else if (repositoryUrl.startsWith(websiteRepoPrefix)) {
            return repositoryUrl.replaceFirst(websiteRepoPrefix, apiRepoPrefix);
        }

        throw new InvalidVcsUrlException(repositoryUrl);
    }

    public static AuthorizationProvider recognizeFromUrl(String url) {
        if (url.startsWith(VCS.GITHUB_WEBSITE_REPOSITORY_PREFIX_ENDPOINT) ||
                url.startsWith(VCS.GITHUB_API_REPOSITORY_PREFIX_ENDPOINT)) {
            return GITHUB;
        }
        if (url.startsWith(VCS.BITBUCKET_WEBSITE_REPOSITORY_PREFIX_ENDPOINT) ||
                url.startsWith(VCS.BITBUCKET_API_REPOSITORY_PREFIX_ENDPOINT)) {
            return BITBUCKET;
        }
        throw new IllegalArgumentException(url);
    }
}

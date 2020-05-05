package com.gmail.maxsvynarchuk.presentation.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileVcsDto {
    private Boolean isAuthorizedGitHub;
    private String gitHubAuthorizationLink;

    private Boolean isAuthorizedBitbucket;
    private String bitbucketAuthorizationLink;
}

package com.gmail.maxsvynarchuk.presentation.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String email;

    // VCS info
    private Boolean isAuthorizedGitHub;
    private String gitHubAuthorizationLink;
    private Boolean isAuthorizedBitbucket;
    private String bitbucketAuthorizationLink;


    // statistic
    private Integer coursesCount;
    private Integer groupsCount;
    private Integer tasksCount;
    private Integer studentsCount;
    private Integer studentsRepositoriesCount;
}

package com.gmail.maxsvynarchuk.presentation.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String email;

    UserProfileVcsDto userProfileVcs;

    // statistic
    private Integer coursesCount;
    private Integer groupsCount;
    private Integer tasksCount;
    private Integer studentsCount;
    private Integer studentsRepositoriesCount;
}

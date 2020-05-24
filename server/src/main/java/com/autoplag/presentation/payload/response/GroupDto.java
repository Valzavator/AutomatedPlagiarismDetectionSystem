package com.autoplag.presentation.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class GroupDto {
    private Long id;
    private String name;
    private Date creationDate;
    private Set<StudentGroupResponseDto> studentGroups;
    private Set<BasicTaskGroupDto> taskGroups;

    private String courseName;
    private String courseId;
}

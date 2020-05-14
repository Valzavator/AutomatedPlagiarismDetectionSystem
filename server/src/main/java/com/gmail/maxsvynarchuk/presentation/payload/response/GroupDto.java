package com.gmail.maxsvynarchuk.presentation.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class GroupDto {
    private Long id;
    private String name;
    private Date creationDate;
    private List<StudentGroupDto> studentGroups;
    private List<BasicTaskGroupDto> taskGroups;

    private String courseName;
    private String courseId;
}

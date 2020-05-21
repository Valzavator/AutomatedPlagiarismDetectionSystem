package com.gmail.maxsvynarchuk.presentation.payload.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StudentGroupDto {
    @NotNull
    private Long groupId;

    @NotNull
    private Long studentId;

    @NotNull
    private Long courseId;

    @NotBlank
    @URL
    private String vcsRepositoryUrl;
}

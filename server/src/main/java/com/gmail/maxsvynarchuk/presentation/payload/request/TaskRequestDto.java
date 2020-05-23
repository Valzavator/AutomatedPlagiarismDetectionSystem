package com.gmail.maxsvynarchuk.presentation.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TaskRequestDto {
    @NotNull
    private Long courseId;
    @NotBlank
    @Size(max = 255)
    private String name;
    @NotNull
    private String repositoryPrefixPath;
    private String description;
}

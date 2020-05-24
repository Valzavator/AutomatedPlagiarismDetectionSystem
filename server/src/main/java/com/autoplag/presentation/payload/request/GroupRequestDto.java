package com.autoplag.presentation.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class GroupRequestDto {
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    private Long courseId;
}

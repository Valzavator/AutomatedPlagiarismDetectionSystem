package com.gmail.maxsvynarchuk.presentation.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CourseRequestDto {
    @NotBlank
    @Size(max = 255)
    private String name;

    private String description;
}

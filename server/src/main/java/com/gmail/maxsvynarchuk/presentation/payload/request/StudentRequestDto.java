package com.gmail.maxsvynarchuk.presentation.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudentRequestDto {
    @NotNull
    private String fullName;
}

package com.gmail.maxsvynarchuk.presentation.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class ProgrammingLanguageDto {
    @NotNull
    private Integer id;

    @NotBlank
    @Size(max = 255)
    private String name;
}

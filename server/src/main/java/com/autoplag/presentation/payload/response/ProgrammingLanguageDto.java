package com.autoplag.presentation.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammingLanguageDto {
    private Integer id;
    private String name;
    private Integer defaultComparisonSensitivity;
    private String fileTypesSupport;
}
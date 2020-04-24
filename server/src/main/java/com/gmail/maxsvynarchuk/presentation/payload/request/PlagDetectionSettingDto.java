package com.gmail.maxsvynarchuk.presentation.payload.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PlagDetectionSettingDto {
    private Long id;

    @NotNull
    @Min(1)
    private Integer comparisonSensitivity;

    @NotNull
    @Min(1)
    private Integer minimumSimilarityPercent;

    private String baseCodePath;

    @NotNull
    private ProgrammingLanguageDto programmingLanguage;
}

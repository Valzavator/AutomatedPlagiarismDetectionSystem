package com.gmail.maxsvynarchuk.presentation.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SingleCheckPlagDetectionSettingsDto {
    @NotNull
    private Integer programmingLanguageId;

    @NotNull
    @Min(1)
    private Integer comparisonSensitivity;

    @NotNull
    @Min(1)
    private Integer minimumSimilarityPercent;

    @NotNull
    private MultipartFile codeToPlagDetectionZip;

    private MultipartFile baseCodeZip;
}

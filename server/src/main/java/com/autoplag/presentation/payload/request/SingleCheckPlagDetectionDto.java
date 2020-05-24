package com.autoplag.presentation.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SingleCheckPlagDetectionDto {
    @NotNull
    private Integer programmingLanguageId;

    @NotNull
    @Min(1)
    private Integer comparisonSensitivity;

    @NotNull
    @Min(1)
    private Integer minimumSimilarityPercent;

    @NotNull
    private Boolean saveLog;

    @NotNull
    private MultipartFile codeToPlagDetectionZip;

    private MultipartFile baseCodeZip;
}

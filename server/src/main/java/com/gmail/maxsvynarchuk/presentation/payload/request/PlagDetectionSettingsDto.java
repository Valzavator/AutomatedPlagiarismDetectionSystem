package com.gmail.maxsvynarchuk.presentation.payload.request;

import com.gmail.maxsvynarchuk.persistence.domain.type.DetectionType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PlagDetectionSettingsDto {
    @NotNull
    private Integer programmingLanguageId;

    @NotNull
    private DetectionType detectionType;

    @NotNull
    @Min(1)
    private Integer comparisonSensitivity;

    @NotNull
    @Min(1)
    private Integer minimumSimilarityPercent;

    private MultipartFile baseCodeZip;
}

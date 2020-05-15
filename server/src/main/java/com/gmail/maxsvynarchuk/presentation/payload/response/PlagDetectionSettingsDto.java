package com.gmail.maxsvynarchuk.presentation.payload.response;

import com.gmail.maxsvynarchuk.persistence.domain.type.DetectionType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlagDetectionSettingsDto {
    private Long id;
    private Integer comparisonSensitivity;
    private Integer minimumSimilarityPercent;
    private boolean isBaseCodePresent;
    private DetectionType detectionType;
    private Boolean saveLog;
    private String programmingLanguageName;
}

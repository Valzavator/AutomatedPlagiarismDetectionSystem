package com.gmail.maxsvynarchuk.persistence.domain;

import com.gmail.maxsvynarchuk.persistence.domain.type.TypeDetection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlagDetectionSetting implements Serializable {
    private Long id;

    private ProgramLanguage programLanguage;

    private Integer comparisonSensitivity;

    private Integer minimumSimilarityPercent;

    private String baseCodePath;

    private TypeDetection typeDetection;

    private String dataPath;

    private String resultPath;

    private TaskGroup taskGroup;
}

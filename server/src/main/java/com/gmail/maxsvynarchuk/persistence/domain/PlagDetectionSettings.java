package com.gmail.maxsvynarchuk.persistence.domain;

import com.gmail.maxsvynarchuk.persistence.domain.type.DetectionType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "plagiarism_detection_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlagDetectionSettings implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plagiarism_detection_setting_id")
    private Long id;

    @NotNull
    @Min(1)
    private Integer comparisonSensitivity;

    @NotNull
    @Min(1)
    private Integer minimumSimilarityPercent;

    private String baseCodePath;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DetectionType detectionType;

    @NotBlank
    private String dataPath;

    @NotBlank
    private String resultPath;

    @NotNull
    private Boolean saveLog;

//    @OneToOne(fetch = FetchType.LAZY,
//            mappedBy = "plagDetectionSetting")
//    @ToString.Exclude
//    private TaskGroup taskGroup;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "programming_language_id", nullable = false)
    private ProgrammingLanguage programmingLanguage;
}

package com.autoplag.presentation.payload.request;

import com.autoplag.persistence.domain.type.DetectionType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class TaskGroupPlagDetectionDto {
    @NotNull
    private Long groupId;

    @NotNull
    private Long taskId;

    @NotNull
    private Date expiryDate;

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

    @NotNull
    private Boolean saveLog;

    private MultipartFile baseCodeZip;
}

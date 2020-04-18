package com.gmail.maxsvynarchuk.persistence.domain;

import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskGroup implements Serializable {
    private Group group;

    private Task task;

    private String creationDate;

    private String expiryDate;

    private PlagDetectionStatus plagDetectionStatus;

    private PlagDetectionSetting plagDetectionSetting;

    private PlagResult plagResult;
}
package com.gmail.maxsvynarchuk.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task implements Serializable {
    private Long id;

    private String name;

    private String description;

    private String creationDate;

    private String expiryDate;

    private Group group;

    private PlagDetectionSetting plagDetectionSetting;

    private PlagResult plagResult;
}
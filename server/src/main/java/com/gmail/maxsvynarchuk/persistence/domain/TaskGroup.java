package com.gmail.maxsvynarchuk.persistence.domain;

import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "task_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskGroup implements Serializable {
    @EmbeddedId
    private TaskGroupKey id;

    @MapsId("group_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @MapsId("task_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @NotNull
    private Date creationDate;

    @NotNull
    private Date expiryDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PlagDetectionStatus plagDetectionStatus;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "plagiarism_detection_setting_id", nullable = false)
    private PlagDetectionSetting plagDetectionSetting;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "plagiarism_detection_result_id")
    private PlagDetectionResult plagDetectionResult;
}
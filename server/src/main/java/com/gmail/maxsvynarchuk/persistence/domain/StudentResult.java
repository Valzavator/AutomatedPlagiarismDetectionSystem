package com.gmail.maxsvynarchuk.persistence.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "student_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResult implements Serializable {
    @EmbeddedId
    private StudentResultKey id;

    @MapsId("plagiarism_detection_result_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "plagiarism_detection_result_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PlagDetectionResult result;

    @MapsId("student_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Student student;

    @NotBlank
    private String message;

}
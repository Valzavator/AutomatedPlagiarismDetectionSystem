package com.autoplag.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public
class ResultStudentKey implements Serializable {
    @Column(name = "plagiarism_detection_result_id")
    private Long resultId;

    @Column(name = "student_id")
    private Long studentId;
}

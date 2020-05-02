package com.gmail.maxsvynarchuk.persistence.domain;

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
class StudentGroupKey implements Serializable {
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "student_id")
    private Long studentId;
}

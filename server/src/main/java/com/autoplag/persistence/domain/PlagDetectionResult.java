package com.autoplag.persistence.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "plagiarism_detection_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlagDetectionResult implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plagiarism_detection_result_id")
    private Long id;

    @Size(max = 255)
    private String resultMessage;

    @Lob
    private String log;

    @NotNull
    private Boolean isSuccessful;

    @Column(length = 1000)
    private String resultPath;

    @NotNull
    private Date date;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "result")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ResultStudent> resultStudents;

    public static PlagDetectionResult failed(String resultMessage) {
        return PlagDetectionResult.builder()
                .date(new Date())
                .isSuccessful(false)
                .resultMessage(resultMessage)
                .build();
    }

    public void addResultStudents(Set<ResultStudent> newResultStudents) {
        if (Objects.isNull(getResultStudents())) {
            resultStudents = new HashSet<>();
        }
        newResultStudents.forEach(rs -> {
            ResultStudentKey resultStudentKey = new ResultStudentKey(id, rs.getStudent().getId());
            rs.setId(resultStudentKey);
            rs.setResult(this);
        });
        resultStudents.addAll(newResultStudents);
    }

}

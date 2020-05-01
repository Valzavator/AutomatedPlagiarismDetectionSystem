package com.gmail.maxsvynarchuk.persistence.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
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
    private String message;

    @NotNull
    private Boolean isSuccessful;

    private String resultPath;

    @NotNull
    private Date date;

//    @OneToOne(fetch = FetchType.LAZY,
//            mappedBy = "plagDetectionResult")
    //    @ToString.Exclude
//    private TaskGroup taskGroup;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "result")
//    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ResultStudent> resultStudents;

    public static PlagDetectionResult failed(String message) {
        return PlagDetectionResult.builder()
                .date(new Date())
                .isSuccessful(false)
                .message(message)
                .build();
    }
}

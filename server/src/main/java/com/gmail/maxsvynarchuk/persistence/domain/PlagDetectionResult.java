package com.gmail.maxsvynarchuk.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

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
}

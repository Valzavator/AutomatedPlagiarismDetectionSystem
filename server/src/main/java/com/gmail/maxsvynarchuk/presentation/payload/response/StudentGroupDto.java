package com.gmail.maxsvynarchuk.presentation.payload.response;

import com.gmail.maxsvynarchuk.persistence.domain.StudentGroupKey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentGroupDto {
    private StudentGroupKey id;
    private Long studentId;
    private String studentFullName;
    private String vcsRepositoryUrl;
}

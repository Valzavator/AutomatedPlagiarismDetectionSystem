package com.gmail.maxsvynarchuk.presentation.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentGroupDto {
    private Long studentId;
    private String studentFullName;
    private String vcsRepositoryUrl;
}

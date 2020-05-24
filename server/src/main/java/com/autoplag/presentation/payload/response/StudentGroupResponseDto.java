package com.autoplag.presentation.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentGroupResponseDto {
    private Long studentId;
    private String studentFullName;
    private String vcsRepositoryUrl;
}

package com.autoplag.presentation.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class PlagDetectionResultDto {
    private String resultMessage;
    private String log;
    private Boolean isSuccessful;
    private String resultPath;
    private Set<ResultStudentDto> resultStudents;
}

package com.autoplag.presentation.payload.response;

import lombok.Data;

@Data
public class SingleCheckPlagDetectionResultDto {
    private String resultMessage;
    private String log;
    private Boolean isSuccessful;
    private String resultPath;
}

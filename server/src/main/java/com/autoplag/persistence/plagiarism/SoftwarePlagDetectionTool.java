package com.autoplag.persistence.plagiarism;

import com.autoplag.persistence.domain.PlagDetectionResult;
import com.autoplag.persistence.domain.PlagDetectionSettings;

public interface SoftwarePlagDetectionTool {

    PlagDetectionResult generateHtmlResult(PlagDetectionSettings configuration);

}

package com.gmail.maxsvynarchuk.persistence.plagiarism;

import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSetting;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;

public interface SoftwarePlagDetectionTool {

    PlagDetectionResult generateHtmlResult(PlagDetectionSetting configuration);

}

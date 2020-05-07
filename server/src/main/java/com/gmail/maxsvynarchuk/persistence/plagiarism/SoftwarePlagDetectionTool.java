package com.gmail.maxsvynarchuk.persistence.plagiarism;

import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSettings;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;

public interface SoftwarePlagDetectionTool {

    PlagDetectionResult generateHtmlResult(PlagDetectionSettings configuration);

}

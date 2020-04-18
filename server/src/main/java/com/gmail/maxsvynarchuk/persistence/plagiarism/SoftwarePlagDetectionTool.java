package com.gmail.maxsvynarchuk.persistence.plagiarism;

import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSetting;
import com.gmail.maxsvynarchuk.persistence.domain.PlagResult;

public interface SoftwarePlagDetectionTool {

    PlagResult generateHtmlResult(PlagDetectionSetting configuration);

}

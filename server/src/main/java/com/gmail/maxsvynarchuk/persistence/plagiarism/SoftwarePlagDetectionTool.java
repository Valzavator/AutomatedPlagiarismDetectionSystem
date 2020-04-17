package com.gmail.maxsvynarchuk.persistence.plagiarism;

import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSetting;

public interface SoftwarePlagDetectionTool {

    boolean generateHtmlResult(PlagDetectionSetting configuration);

}

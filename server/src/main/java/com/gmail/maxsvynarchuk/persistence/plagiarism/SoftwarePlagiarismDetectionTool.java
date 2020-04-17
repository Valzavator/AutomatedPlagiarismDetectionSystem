package com.gmail.maxsvynarchuk.persistence.plagiarism;

import com.gmail.maxsvynarchuk.persistence.domain.PlagiarismDetectionConfiguration;

public interface SoftwarePlagiarismDetectionTool {

    boolean generateHtmlResult(PlagiarismDetectionConfiguration configuration);

}

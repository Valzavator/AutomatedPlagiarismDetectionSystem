package com.gmail.maxsvynarchuk.persistence.plagiarism.jplag;

import com.gmail.maxsvynarchuk.persistence.domain.PlagiarismDetectionConfiguration;
import com.gmail.maxsvynarchuk.persistence.plagiarism.SoftwarePlagiarismDetectionTool;

public class SoftwarePlagiarismDetectionToolImpl implements SoftwarePlagiarismDetectionTool {

    @Override
    public boolean generateHtmlResult(PlagiarismDetectionConfiguration configuration) {
        return false;
    }

}

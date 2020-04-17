package com.gmail.maxsvynarchuk.persistence.plagiarism.jplag;

import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionConfiguration;
import com.gmail.maxsvynarchuk.persistence.plagiarism.SoftwarePlagDetectionTool;

public class SoftwarePlagDetectionToolImpl implements SoftwarePlagDetectionTool {

    @Override
    public boolean generateHtmlResult(PlagDetectionConfiguration configuration) {
        return false;
    }

}

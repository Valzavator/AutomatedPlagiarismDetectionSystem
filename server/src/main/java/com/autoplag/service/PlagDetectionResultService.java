package com.autoplag.service;

import com.autoplag.persistence.domain.PlagDetectionResult;

public interface PlagDetectionResultService {

    PlagDetectionResult savePlagDetectionResult(PlagDetectionResult result);

}

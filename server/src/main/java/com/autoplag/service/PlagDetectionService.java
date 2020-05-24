package com.autoplag.service;

import com.autoplag.persistence.domain.PlagDetectionResult;
import com.autoplag.persistence.domain.PlagDetectionSettings;
import com.autoplag.persistence.domain.TaskGroup;

public interface PlagDetectionService {

    PlagDetectionResult processForTaskGroup(TaskGroup taskGroup);

    PlagDetectionResult processForSingleTask(PlagDetectionSettings setting);

}

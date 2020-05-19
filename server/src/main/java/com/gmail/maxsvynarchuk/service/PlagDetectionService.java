package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSettings;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;

public interface PlagDetectionService {

    PlagDetectionResult processForTaskGroup(TaskGroup taskGroup);

    PlagDetectionResult processForSingleTask(PlagDetectionSettings setting);

}

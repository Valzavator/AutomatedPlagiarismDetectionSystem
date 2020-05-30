package com.autoplag.service;

import com.autoplag.persistence.domain.PlagDetectionResult;
import com.autoplag.persistence.domain.PlagDetectionSettings;
import com.autoplag.persistence.domain.TaskGroup;
import org.springframework.web.multipart.MultipartFile;

public interface PlagDetectionService {

    PlagDetectionResult processForTaskGroup(TaskGroup taskGroup);

    PlagDetectionResult processForSingleTask(PlagDetectionSettings setting,
                                             MultipartFile codeToPlagDetectionZip,
                                             MultipartFile baseCodeZip);

}

package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.presentation.payload.request.PlagDetectionSettingDto;
import org.springframework.web.multipart.MultipartFile;

public interface SinglePlagiarismDetectionFacade {

    PlagDetectionResult processForZipFile(PlagDetectionSettingDto settingDto, MultipartFile multipartFile);

}

package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionSettingsDto;
import org.springframework.web.multipart.MultipartFile;

public interface SinglePlagiarismDetectionFacade {

    PlagDetectionResult processForZipFile(SingleCheckPlagDetectionSettingsDto settingDto, MultipartFile multipartFile);

}

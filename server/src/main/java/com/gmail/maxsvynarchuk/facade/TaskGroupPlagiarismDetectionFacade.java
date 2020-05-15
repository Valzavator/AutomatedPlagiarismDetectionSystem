package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSettingsDto;

public interface TaskGroupPlagiarismDetectionFacade {

    OptionsForSettingsDto getOptionsForSettings(Long courseId);

//    SingleCheckPlagDetectionResultDto processSingleCheck(SingleCheckPlagDetectionDto dto);

}

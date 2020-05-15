package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.SingleCheckPlagDetectionResultDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSingleCheckSettingsDto;

public interface SingleCheckPlagiarismDetectionFacade {

    OptionsForSingleCheckSettingsDto getOptionsForSingleCheckSettings();

    SingleCheckPlagDetectionResultDto processSingleCheck(SingleCheckPlagDetectionDto dto);

}

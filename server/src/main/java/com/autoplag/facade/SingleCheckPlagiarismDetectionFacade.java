package com.autoplag.facade;

import com.autoplag.presentation.payload.request.SingleCheckPlagDetectionDto;
import com.autoplag.presentation.payload.response.OptionsForSingleCheckSettingsDto;
import com.autoplag.presentation.payload.response.SingleCheckPlagDetectionResultDto;

public interface SingleCheckPlagiarismDetectionFacade {

    OptionsForSingleCheckSettingsDto getOptionsForSingleCheckSettings();

    SingleCheckPlagDetectionResultDto processSingleCheck(SingleCheckPlagDetectionDto dto);

}

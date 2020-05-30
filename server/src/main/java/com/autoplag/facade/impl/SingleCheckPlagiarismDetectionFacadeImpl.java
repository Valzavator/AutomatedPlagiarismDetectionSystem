package com.autoplag.facade.impl;

import com.autoplag.facade.Facade;
import com.autoplag.facade.SingleCheckPlagiarismDetectionFacade;
import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.PlagDetectionResult;
import com.autoplag.persistence.domain.PlagDetectionSettings;
import com.autoplag.persistence.domain.ProgrammingLanguage;
import com.autoplag.presentation.payload.request.SingleCheckPlagDetectionDto;
import com.autoplag.presentation.payload.response.OptionsForSingleCheckSettingsDto;
import com.autoplag.presentation.payload.response.ProgrammingLanguageDto;
import com.autoplag.presentation.payload.response.SingleCheckPlagDetectionResultDto;
import com.autoplag.service.PlagDetectionService;
import com.autoplag.service.ProgrammingLanguageService;
import lombok.AllArgsConstructor;

import java.util.List;

@Facade
@AllArgsConstructor
public class SingleCheckPlagiarismDetectionFacadeImpl implements SingleCheckPlagiarismDetectionFacade {

    private final ProgrammingLanguageService programmingLanguageService;
    private final PlagDetectionService plagiarismDetectionService;

    private final Converter<ProgrammingLanguage, ProgrammingLanguageDto> languageConverter;
    private final Converter<SingleCheckPlagDetectionDto, PlagDetectionSettings> settingsConverter;
    private final Converter<PlagDetectionResult, SingleCheckPlagDetectionResultDto> resultConverter;

    @Override
    public OptionsForSingleCheckSettingsDto getOptionsForSingleCheckSettings() {
        List<ProgrammingLanguage> languages = programmingLanguageService.getAllProgrammingLanguages();
        List<ProgrammingLanguageDto> languageDtos = languageConverter.convertAll(languages);
        return new OptionsForSingleCheckSettingsDto(languageDtos);
    }

    @Override
    public SingleCheckPlagDetectionResultDto processSingleCheck(SingleCheckPlagDetectionDto dto) {
        ProgrammingLanguage programmingLanguage = programmingLanguageService
                .getProgrammingLanguageById(dto.getProgrammingLanguageId());
        PlagDetectionSettings settings = settingsConverter.convert(dto);
        settings.setProgrammingLanguage(programmingLanguage);

        PlagDetectionResult result = plagiarismDetectionService.processForSingleTask(settings,
                dto.getCodeToPlagDetectionZip(),
                dto.getBaseCodeZip());
        return resultConverter.convert(result);
    }

}

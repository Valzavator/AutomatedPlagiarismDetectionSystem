package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.PlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.ProgrammingLanguage;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSingleCheckSettingsDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.ProgrammingLanguageDto;
import com.gmail.maxsvynarchuk.service.ProgrammingLanguageService;
import lombok.AllArgsConstructor;

import java.util.List;

@Facade
@AllArgsConstructor
public class PlagiarismDetectionFacadeImpl implements PlagiarismDetectionFacade {
//    private final ProgrammingLanguageService programmingLanguageService;
//
//    private final Converter<ProgrammingLanguage, ProgrammingLanguageDto> converter;
//
//
//    @Override
//    public OptionsForSingleCheckSettingsDto getOptionsForSingleCheckSettings() {
//        List<ProgrammingLanguage> languages = programmingLanguageService.getAllProgrammingLanguages();
//        List<ProgrammingLanguageDto> languagesDto = List.copyOf(converter.convertAll(languages));
//        return new OptionsForSingleCheckSettingsDto(languagesDto);
//    }

}

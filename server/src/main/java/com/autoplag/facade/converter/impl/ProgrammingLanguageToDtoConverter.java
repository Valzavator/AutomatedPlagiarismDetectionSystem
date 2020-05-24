package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.ProgrammingLanguage;
import com.autoplag.presentation.payload.response.ProgrammingLanguageDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProgrammingLanguageToDtoConverter implements Converter<ProgrammingLanguage, ProgrammingLanguageDto> {
    private final ModelMapper mapper;

    @Override
    public ProgrammingLanguageDto convert(ProgrammingLanguage programmingLanguage) {
        return mapper.map(programmingLanguage, ProgrammingLanguageDto.class);
    }
}

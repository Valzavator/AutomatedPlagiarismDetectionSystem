package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.PlagDetectionResult;
import com.autoplag.presentation.payload.response.SingleCheckPlagDetectionResultDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PlagDetectionResultToSingleCheckPlagDetectionResultDto
        implements Converter<PlagDetectionResult, SingleCheckPlagDetectionResultDto> {
    private final ModelMapper mapper;

    @Override
    public SingleCheckPlagDetectionResultDto convert(PlagDetectionResult plagDetectionResult) {
        return mapper.map(plagDetectionResult, SingleCheckPlagDetectionResultDto.class);
    }
}

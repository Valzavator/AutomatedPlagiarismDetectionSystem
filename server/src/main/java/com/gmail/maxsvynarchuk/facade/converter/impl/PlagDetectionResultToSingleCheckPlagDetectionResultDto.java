package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionResultDto;
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

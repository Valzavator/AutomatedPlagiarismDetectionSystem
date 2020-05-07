package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSettings;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SingleCheckPlagDetectionDtoToPlagDetectionSettingConverter
        implements Converter<SingleCheckPlagDetectionDto, PlagDetectionSettings> {
    private final ModelMapper mapper;

    @Override
    public PlagDetectionSettings convert(SingleCheckPlagDetectionDto dto) {
        return PlagDetectionSettings.builder()
                .minimumSimilarityPercent(dto.getMinimumSimilarityPercent())
                .comparisonSensitivity(dto.getComparisonSensitivity())
                .saveLog(dto.getSaveLog())
                .build();
    }
}

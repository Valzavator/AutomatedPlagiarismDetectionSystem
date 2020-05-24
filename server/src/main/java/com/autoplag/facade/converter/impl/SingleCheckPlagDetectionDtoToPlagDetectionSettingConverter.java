package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.PlagDetectionSettings;
import com.autoplag.presentation.payload.request.SingleCheckPlagDetectionDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SingleCheckPlagDetectionDtoToPlagDetectionSettingConverter
        implements Converter<SingleCheckPlagDetectionDto, PlagDetectionSettings> {

    @Override
    public PlagDetectionSettings convert(SingleCheckPlagDetectionDto dto) {
        return PlagDetectionSettings.builder()
                .minimumSimilarityPercent(dto.getMinimumSimilarityPercent())
                .comparisonSensitivity(dto.getComparisonSensitivity())
                .saveLog(dto.getSaveLog())
                .build();
    }
}

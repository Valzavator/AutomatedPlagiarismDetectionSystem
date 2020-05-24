package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.PlagDetectionSettings;
import com.autoplag.presentation.payload.request.TaskGroupPlagDetectionDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskGroupPlagDetectionDtoToPlagDetectionSettingConverter
        implements Converter<TaskGroupPlagDetectionDto, PlagDetectionSettings> {

    @Override
    public PlagDetectionSettings convert(TaskGroupPlagDetectionDto dto) {
        return PlagDetectionSettings.builder()
                .detectionType(dto.getDetectionType())
                .minimumSimilarityPercent(dto.getMinimumSimilarityPercent())
                .comparisonSensitivity(dto.getComparisonSensitivity())
                .saveLog(dto.getSaveLog())
                .build();
    }
}

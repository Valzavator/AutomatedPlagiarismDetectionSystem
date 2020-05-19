package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSettings;
import com.gmail.maxsvynarchuk.presentation.payload.request.TaskGroupPlagDetectionDto;
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

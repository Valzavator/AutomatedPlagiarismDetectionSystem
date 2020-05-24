package com.autoplag.presentation.payload.response;

import com.autoplag.persistence.domain.type.DetectionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OptionsForSettingsDto {
    private List<ProgrammingLanguageDto> languages;
    private List<BasicTaskDto> tasks;
    private final List<DetectionType> detectionTypes = List.of(DetectionType.values());
}

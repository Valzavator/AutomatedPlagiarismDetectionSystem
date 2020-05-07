package com.gmail.maxsvynarchuk.presentation.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OptionsForSingleCheckSettingsDto {
    private List<ProgrammingLanguageDto> languages;
}

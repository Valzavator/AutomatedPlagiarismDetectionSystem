package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSettings;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionSettingsDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlagDetectionSettingDtoToPlagDetectionSettingConverter
        implements Converter<SingleCheckPlagDetectionSettingsDto, PlagDetectionSettings> {
    private final ModelMapper mapper;

    @Override
    public PlagDetectionSettings convert(SingleCheckPlagDetectionSettingsDto plagDetectionSettingDto) {
        return mapper.map(plagDetectionSettingDto, PlagDetectionSettings.class);
    }

}

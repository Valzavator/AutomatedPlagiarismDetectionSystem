package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSetting;
import com.gmail.maxsvynarchuk.presentation.payload.request.PlagDetectionSettingDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlagDetectionSettingDtoToPlagDetectionSettingConverter
        implements Converter<PlagDetectionSettingDto, PlagDetectionSetting> {
    private final ModelMapper mapper;

    @Override
    public PlagDetectionSetting convert(PlagDetectionSettingDto plagDetectionSettingDto) {
        return mapper.map(plagDetectionSettingDto, PlagDetectionSetting.class);
    }

}

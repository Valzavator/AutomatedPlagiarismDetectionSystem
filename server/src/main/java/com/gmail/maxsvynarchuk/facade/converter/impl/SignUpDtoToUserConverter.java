package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.presentation.payload.request.SignUpDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SignUpDtoToUserConverter implements Converter<SignUpDto, User> {
    private final ModelMapper mapper;

    @Override
    public User convert(SignUpDto signUpDto) {
        return mapper.map(signUpDto, User.class);
    }
}

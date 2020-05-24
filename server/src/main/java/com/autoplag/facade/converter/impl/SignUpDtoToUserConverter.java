package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.User;
import com.autoplag.presentation.payload.request.SignUpDto;
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

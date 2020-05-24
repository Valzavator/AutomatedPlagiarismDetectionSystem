package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.User;
import com.autoplag.persistence.domain.type.AuthorizationProvider;
import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.presentation.payload.response.UserProfileDto;
import com.autoplag.presentation.payload.response.UserProfileVcsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@AllArgsConstructor
public class UserToUserProfileDtoConverter implements Converter<User, UserProfileDto> {
    private final Converter<User, UserProfileVcsDto> userToUserProfileVcsDto;

    @Override
    public UserProfileDto convert(User user) {
        UserProfileVcsDto userProfileVcsDto =  userToUserProfileVcsDto.convert(user);

        return UserProfileDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userProfileVcs(userProfileVcsDto)
                .build();
    }

    private boolean isTokenPresented(Collection<AccessToken> tokens, AuthorizationProvider provider) {
        return tokens.stream()
                .anyMatch(token -> provider == token.getAuthorizationProvider());
    }
}

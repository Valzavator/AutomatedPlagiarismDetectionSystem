package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.presentation.payload.response.UserProfileDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.UserProfileVcsDto;
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

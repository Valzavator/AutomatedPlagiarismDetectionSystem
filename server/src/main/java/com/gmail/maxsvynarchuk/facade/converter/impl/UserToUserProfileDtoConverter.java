package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.presentation.payload.response.UserProfileDto;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserToUserProfileDtoConverter implements Converter<User, UserProfileDto> {

    @Override
    public UserProfileDto convert(User user) {
        boolean isAuthorizedGitHub = isTokenPresented(user.getTokens(), AuthorizationProvider.GITHUB);
        boolean isAuthorizedBitbucket =isTokenPresented(user.getTokens(), AuthorizationProvider.BITBUCKET);

        return UserProfileDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .isAuthorizedGitHub(isAuthorizedGitHub)
                .isAuthorizedBitbucket(isAuthorizedBitbucket)
                .build();
    }

    private boolean isTokenPresented(Collection<AccessToken> tokens, AuthorizationProvider provider) {
        return tokens.stream()
                .anyMatch(token -> provider == token.getAuthorizationProvider());
    }
}

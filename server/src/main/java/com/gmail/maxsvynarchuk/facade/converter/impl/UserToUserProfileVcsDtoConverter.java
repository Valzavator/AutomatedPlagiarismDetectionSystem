package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.presentation.payload.response.UserProfileDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.UserProfileVcsDto;
import com.gmail.maxsvynarchuk.service.vcs.VcsOAuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@AllArgsConstructor
public class UserToUserProfileVcsDtoConverter implements Converter<User, UserProfileVcsDto> {
    @Qualifier("vcsOAuthBitbucketService")
    private final VcsOAuthService vcsOAuthBitbucketService;
    @Qualifier("vcsOAuthGitHubService")
    private final VcsOAuthService vcsOAuthGitHubService;

    @Override
    public UserProfileVcsDto convert(User user) {
        boolean isAuthorizedGitHub = user.isAccessTokenPresented(AuthorizationProvider.GITHUB);
        boolean isAuthorizedBitbucket = user.isAccessTokenPresented(AuthorizationProvider.BITBUCKET);

        return UserProfileVcsDto.builder()
                .isAuthorizedGitHub(isAuthorizedGitHub)
                .isAuthorizedBitbucket(isAuthorizedBitbucket)
                .gitHubAuthorizationLink(vcsOAuthGitHubService.getAuthorizeOAuthUrl(user.getId()))
                .bitbucketAuthorizationLink(vcsOAuthBitbucketService.getAuthorizeOAuthUrl(user.getId()))
                .build();
    }

}

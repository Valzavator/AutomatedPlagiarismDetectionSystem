package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.User;
import com.autoplag.persistence.domain.type.AuthorizationProvider;
import com.autoplag.presentation.payload.response.UserProfileVcsDto;
import com.autoplag.service.vcs.VcsOAuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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

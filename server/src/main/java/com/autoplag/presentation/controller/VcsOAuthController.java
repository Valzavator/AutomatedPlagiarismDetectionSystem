package com.autoplag.presentation.controller;

import com.autoplag.persistence.domain.User;
import com.autoplag.persistence.domain.type.AuthorizationProvider;
import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.presentation.security.AuthUser;
import com.autoplag.presentation.security.serivce.UserPrincipal;
import com.autoplag.presentation.util.ControllerUtil;
import com.autoplag.service.UserService;
import com.autoplag.service.vcs.VcsOAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/v1/vcs")
@AllArgsConstructor
@Slf4j
public class VcsOAuthController {
    private final UserService userService;
    @Qualifier("vcsOAuthBitbucketService")
    private final VcsOAuthService vcsOAuthBitbucketService;
    @Qualifier("vcsOAuthGitHubService")
    private final VcsOAuthService vcsOAuthGitHubService;

    @GetMapping("/code/github/{userId}")
    public String addGitHubToken(@PathVariable("userId") Long userId,
                                 @RequestParam String code,
                                 @RequestParam String state) {
        User user = userService.getUserById(userId);
        if (!user.isAccessTokenPresented(AuthorizationProvider.GITHUB)) {
            AccessToken accessToken = vcsOAuthGitHubService.getAuthorizeOAuthToken(code, state);
            userService.addAccessTokenToUser(user, accessToken);
        } else {
            log.error("User already have access token for GitHub service!");
            throw new IllegalStateException("User already have access token for GitHub service!");
        }

        return ControllerUtil.redirectTo("http://localhost:8081/close");
    }

    @GetMapping("/code/bitbucket/{userId}")
    public String saveBitbucketToken(@PathVariable("userId") Long userId,
                                     @RequestParam String code,
                                     HttpServletRequest request) {
        User user = userService.getUserById(userId);
        if (!user.isAccessTokenPresented(AuthorizationProvider.BITBUCKET)) {
            AccessToken accessToken = vcsOAuthBitbucketService.getAuthorizeOAuthToken(
                    code, request.getRequestURL().toString());
            userService.addAccessTokenToUser(user, accessToken);
        } else {
            log.error("User already have access token for Bitbucket service!");
        }

        return ControllerUtil.redirectTo("http://localhost:8081/close");
    }

    @PostMapping("/delete/{authorizationProvider}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteToken(
            @AuthUser UserPrincipal currentUser,
            @PathVariable("authorizationProvider") AuthorizationProvider authorizationProvider,
            HttpServletRequest request) {
        User user = userService.getUserById(currentUser.getId());
        boolean isSuccess = userService.deleteAccessTokenToUser(user, authorizationProvider);
        return isSuccess
                ? ResponseEntity.noContent().build()
                : ControllerUtil.notFoundError(request.getRequestURI());
    }

}

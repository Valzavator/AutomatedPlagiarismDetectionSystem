package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import com.gmail.maxsvynarchuk.service.vcs.VcsOAuthService;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/v1/oauth2")
@AllArgsConstructor
@Slf4j
public class VcsOAuthController {
    @Qualifier("vcsOAuthBitbucketService")
    private final VcsOAuthService vcsOAuthBitbucketService;
    @Qualifier("vcsOAuthGitHubService")
    private final VcsOAuthService vcsOAuthGitHubService;

//    @GetMapping("/github/authorize")
//    @PreAuthorize()
//    public String getGitHubAuthorizeUrl() {
//        return ControllerUtil.redirectTo(
//                vcsOAuthGitHubService.getAuthorizeOAuthUrl());
//    }

    @GetMapping("/code/github/{userId}")
    public String getGitHubToken(@PathVariable("userId") Long userId,
                                 @RequestParam String code,
                                 @RequestParam String state) {

        //TODO - check token in user before request


        System.out.println("\n\n\n");
        System.out.println("\tCODE: " + code);
        System.out.println(userId);
//        System.out.println(accessToken);
        System.out.println("\n\n\n");

        AccessToken accessToken = vcsOAuthGitHubService.getAuthorizeOAuthToken(code, state);


        String res = Unirest.get("https://api.github.com/user/repos?affiliation=collaborator")
                .header("Authorization", accessToken.getAccessToken())
                .header("Accept", "application/vnd.github.v3+json")
                .asString()
                .getBody();

        System.out.println(res);

        return ControllerUtil.redirectTo("/");
    }

//    @GetMapping("/bitbucket/authorize")
//    public String getBitbucketAuthorizeUrl() {
//        return ControllerUtil.redirectTo(
//                vcsOAuthBitbucketService.getAuthorizeOAuthUrl());
//    }

    @GetMapping("/code/bitbucket/{userId}")
    public String getBitbucketToken(@PathVariable("userId") Long userId,
                                    @RequestParam String code,
                                    HttpServletRequest request) {

        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL().toString());
        //TODO - check token in user before request


        AccessToken accessToken = vcsOAuthBitbucketService.getAuthorizeOAuthToken(
                code, request.getRequestURL().toString());

        System.out.println(accessToken);

        String res1 = Unirest.get("https://api.bitbucket.org/2.0/repositories/Muguvara/test_api_oauth")
                .header("Authorization", accessToken.getAccessToken())
                .asString()
                .getBody();

        System.out.println(res1);
        System.out.println("\n\n\n");

        return ControllerUtil.redirectTo("/");
    }

}

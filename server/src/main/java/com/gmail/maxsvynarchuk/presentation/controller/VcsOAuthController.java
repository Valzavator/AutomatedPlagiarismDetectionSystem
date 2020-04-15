package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.domain.AccessToken;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsOAuthDao;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import com.gmail.maxsvynarchuk.service.vcs.VcsOAuthService;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/oauth2")
@AllArgsConstructor
public class VcsOAuthController {
    private final VcsOAuthService vcsOAuthService;
    private final VcsOAuthDao vcsOAuthBitbucketDao;

    @GetMapping("/github/authorize")
    public String getGitHubAuthorizeUrl() {
        return ControllerUtil.redirectTo(
                vcsOAuthService.getAuthorizeOAuthUrl());
    }

    @GetMapping("/code/github")
    public String getGitHubToken(@RequestParam String code, @RequestParam String state) {
//        String accessToken = Unirest.post("https://github.com/login/oauth/access_token")
//                .queryString("client_id", "f74b031291e5a5f5067f")
//                .queryString("client_secret", "cb1d8707113ecb73e0a5d4744086a1471e67c2ac")
//                .queryString("code", code)
//                .queryString("state", state)
//                .header("Accept", "application/json")
//                .asJson()
//                .getBody()
//                .getObject()
//                .get("access_token")
//                .toString();

        AccessToken accessToken = vcsOAuthService.getAuthorizeOAuthToken(code, state);

        System.out.println(accessToken);

        String res = Unirest.get("https://api.github.com/user/repos?affiliation=collaborator")
                .header("Authorization", accessToken.getAccessToken())
                .header("Accept", "application/vnd.github.v3+json")
                .asString()
                .getBody();

        System.out.println(res);

        return ControllerUtil.redirectTo("/");
    }

    @GetMapping("/bitbucket/authorize")
    public String getBitbucketAuthorizeUrl() {
        return ControllerUtil.redirectTo(
                vcsOAuthBitbucketDao.getAuthorizeOAuthUrl());
    }

    @GetMapping("/code/bitbucket")
    public String getBitbucketToken(@RequestParam String code) {
//        String accessToken = Unirest.post("https://github.com/login/oauth/access_token")
//                .queryString("client_id", "f74b031291e5a5f5067f")
//                .queryString("client_secret", "cb1d8707113ecb73e0a5d4744086a1471e67c2ac")
//                .queryString("code", code)
//                .queryString("state", state)
//                .header("Accept", "application/json")
//                .asJson()
//                .getBody()
//                .getObject()
//                .get("access_token")
//                .toString();

        AccessToken accessToken = vcsOAuthBitbucketDao.getAuthorizeOAuthToken(code, "");

        System.out.println(accessToken);

        String res1 = Unirest.get("https://api.bitbucket.org/2.0/repositories/Muguvara/test_api_oauth")
                .header("Authorization", accessToken.getAccessToken())
                .asString()
                .getBody();

        System.out.println(res1);
        System.out.println("\n\n\n");

//        accessToken = vcsOAuthBitbucketDao.getRefreshedOAuthToken(accessToken);
//        System.out.println(accessToken);
//
//        String res2 = Unirest.get("https://api.bitbucket.org/2.0/repositories/Muguvara/test_api_oauth")
//                .header("Authorization", accessToken.getAccessToken())
//                .asString()
//                .getBody();
//
//        System.out.println(res2);

        return ControllerUtil.redirectTo("/");
    }

    @GetMapping("/test")
    public void test() {
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessTokenString("119d33f6aaa20f3a7f2d397a76f433043b83dbb2");
        accessToken.setTokenType("bearer");
//        vcsRepositoryDao.getRepositoryInfo(accessToken, "asd");
    }

}

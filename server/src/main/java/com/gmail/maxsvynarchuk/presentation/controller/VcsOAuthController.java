package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.dao.UserDao;
import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.persistence.domain.type.Gender;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsOAuthDao;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import com.gmail.maxsvynarchuk.service.vcs.VcsOAuthService;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/oauth2")
@AllArgsConstructor
//@Slf4j
public class VcsOAuthController {
    private static final Logger logger = LogManager.getLogger(VcsOAuthController.class);


    @Qualifier("vcsOAuthBitbucketService")
    private final VcsOAuthService vcsOAuthBitbucketService;
    @Qualifier("vcsOAuthGitHubService")
    private final VcsOAuthService vcsOAuthGitHubService;

    @GetMapping("/github/authorize")
    public String getGitHubAuthorizeUrl() {
        return ControllerUtil.redirectTo(
                vcsOAuthGitHubService.getAuthorizeOAuthUrl());
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

        AccessToken accessToken = vcsOAuthGitHubService.getAuthorizeOAuthToken(code, state);

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
                vcsOAuthBitbucketService.getAuthorizeOAuthUrl());
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

        AccessToken accessToken = vcsOAuthBitbucketService.getAuthorizeOAuthToken(code, "");

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

    private UserDao userDao;

    @GetMapping("/test")
    public void test() {
        logger.error("TEST");
        User user = User.builder()
                .dateOfBirth(new Date())
                .email("email")
                .firstName("firstName")
                .lastName("lastName")
                .gender(Gender.FEMALE)
                .password("111")
                .build();

        userDao.save(user);

//        vcsRepositoryDao.getRepositoryInfo(accessToken, "asd");
    }

}

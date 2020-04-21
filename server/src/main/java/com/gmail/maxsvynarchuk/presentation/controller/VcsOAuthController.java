package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.dao.*;
import com.gmail.maxsvynarchuk.persistence.domain.Task;
import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import com.gmail.maxsvynarchuk.service.vcs.VcsOAuthService;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/oauth2")
@AllArgsConstructor
@Slf4j
public class VcsOAuthController {

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

    private RoleDao roleDao;
    private UserDao userDao;
    private AccessTokenDao accessTokenDao;

    @GetMapping("/test")
    public void test() {
        log.error("TEST");

//        Role role = new Role();
//        role.setName("Test");
//        roleDao.save(role);

//        User user = User.builder()
//                .dateOfBirth(new Date())
//                .email("email@email.com")
//                .firstName("firstName")
//                .lastName("lastName")
//                .gender(Gender.FEMALE)
//                .password("123456")
//                .role(role)
//                .build();
//        User user = userDao.findOneByEmail("email@email.com").get();
//        System.out.println("\n" + userDao.findOneByEmail("email@email.com").get());
        //
        User user = userDao.findByEmail("max@gmail.com").get();
        System.out.println("\n\n\n");
        System.out.println(user);
//        userDao.delete();
//
//        AccessToken accessToken = new AccessToken();
//        accessToken.setScope("scopescope");
//        accessToken.setAccessTokenString("qweqwe");
//        accessToken.setTokenType("BearerBearer");
//        accessToken.setAuthorizationProvider(AuthorizationProvider.BITBUCKET);
//        accessToken.setUser(user);

//        AccessToken accessToken = accessTokenDao.findOne(6L).get();

//        System.out.println("\n" + accessToken);
//        accessTokenDao.delete(accessToken);

//        System.out.println("\n" + userDao.findOneByEmail("email@email.com").get());

//        accessTokenDao.delete(accessToken);

//        System.out.println("\n" + userDao.findOneByEmail("email@email.com").get());


//        vcsRepositoryDao.getRepositoryInfo(accessToken, "asd");

//        Role role = roleDao.findOne(2).get();
//        roleDao.delete(role);
    }

    private StudentDao studentDao;
    private GroupDao groupDao;
    private TaskDao taskDao;
    @GetMapping("test1/{id}")
    public void test1(@PathVariable Long id) {
        System.out.println(groupDao.findAll());

        Task task = taskDao.findOne(id).get();
        System.out.println(task);

        System.out.println(studentDao.findAllWhoHaveTask(task));
    }

}

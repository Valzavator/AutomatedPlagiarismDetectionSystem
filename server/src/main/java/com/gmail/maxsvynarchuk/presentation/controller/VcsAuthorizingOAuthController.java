package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.vcs.VcsAuthorizingOAuthFacade;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import kong.unirest.Unirest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/oauth2")
public class VcsAuthorizingOAuthController {
    private final VcsAuthorizingOAuthFacade vcsAuthorizingOAuthFacade;
    private String userState = "asdasd";

    public VcsAuthorizingOAuthController(VcsAuthorizingOAuthFacade vcsAuthorizingOAuthFacade) {
        this.vcsAuthorizingOAuthFacade = vcsAuthorizingOAuthFacade;
    }

    @GetMapping("/authorize")
    public String getAuthorizeUrl() {
        return ControllerUtil.redirectTo(
                vcsAuthorizingOAuthFacade.getAuthorizeOAuthURL(userState));
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

        String accessToken = vcsAuthorizingOAuthFacade.getAuthorizeOAuthToken(code, userState, state);

        System.out.println(accessToken);

        String res = Unirest.get("https://api.github.com/user/repos?affiliation=collaborator")
                .header("Authorization", accessToken)
                .header("Accept", "application/vnd.github.v3+json")
                .asString()
                .getBody();

        System.out.println(res);

        return ControllerUtil.redirectTo("/");
    }

}

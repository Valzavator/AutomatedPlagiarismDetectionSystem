package com.gmail.maxsvynarchuk.controller;

//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Controller
public class OAuth2 {

    @GetMapping("test/authorize")
    public String getAuthorizeUrl() {
        return "redirect:https://github.com/login/oauth/authorize?client_id=f74b031291e5a5f5067f&scope=repo&state=qweqweqweqw";
    }

    @GetMapping("login/test/code/github")
    public String getGitHubToken(@RequestParam String code, @RequestParam String state) {
        System.out.println(code);
        System.out.println(state);

        String accessToken = Unirest.post("https://github.com/login/oauth/access_token")
                .queryString("client_id", "f74b031291e5a5f5067f")
                .queryString("client_secret", "cb1d8707113ecb73e0a5d4744086a1471e67c2ac")
                .queryString("code", code)
                .queryString("state", state)
                .header("Accept", "application/json")
                .asJson()
                .getBody()
                .getObject()
                .get("access_token")
                .toString();

        System.out.println(accessToken);

        String res = Unirest.get("https://api.github.com/user/repos?affiliation=collaborator")
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/vnd.github.v3+json")
                .asString()
                .getBody();

        System.out.println(res);

        return "redirect:/";
    }

}

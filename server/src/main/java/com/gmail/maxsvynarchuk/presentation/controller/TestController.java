package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.presentation.security.AuthUser;
import com.gmail.maxsvynarchuk.presentation.security.serivce.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() throws IOException {
        File file = ResourceUtils.getFile("classpath:");
        System.out.println(file.isFile());
        System.out.println(file.exists());
        System.out.println(file.getPath());
        System.out.println(file.getCanonicalPath());
        System.out.println(file.getAbsolutePath());
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess(@AuthUser UserPrincipal currentUser) {
        System.out.println(currentUser);
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}

package com.autoplag.presentation.controller;

import com.autoplag.facade.UserFacade;
import com.autoplag.presentation.payload.response.BasicUserDto;
import com.autoplag.presentation.payload.response.UserProfileDto;
import com.autoplag.presentation.payload.response.UserProfileVcsDto;
import com.autoplag.presentation.security.AuthUser;
import com.autoplag.presentation.security.serivce.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserFacade userFacade;

    @GetMapping("/basic")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BasicUserDto> getBasicUserInfo(@AuthUser UserPrincipal currentUser) {
        return ResponseEntity.ok()
                .body(new BasicUserDto(
                        currentUser.getUsername(),
                        currentUser.getEmail()));
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserProfileDto> getUserProfileInfo(@AuthUser UserPrincipal currentUser) {
        UserProfileDto userProfileDto = userFacade.getUserProfile(currentUser.getId());
        return ResponseEntity.ok()
                .body(userProfileDto);
    }

    @GetMapping("/profile/vcs")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserProfileVcsDto> getUserProfileVcsInfo(@AuthUser UserPrincipal currentUser) {
        UserProfileVcsDto userProfileVcsDto = userFacade.getUserProfileVcs(currentUser.getId());
        return ResponseEntity.ok()
                .body(userProfileVcsDto);
    }

}

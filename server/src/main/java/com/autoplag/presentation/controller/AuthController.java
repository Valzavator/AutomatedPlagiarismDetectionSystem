package com.autoplag.presentation.controller;

import com.autoplag.facade.UserFacade;
import com.autoplag.presentation.payload.request.LoginDto;
import com.autoplag.presentation.payload.request.SignUpDto;
import com.autoplag.presentation.payload.response.ApiSignUpDto;
import com.autoplag.presentation.payload.response.JwtAuthenticationDto;
import com.autoplag.presentation.security.jwt.JwtTokenProvider;
import com.autoplag.presentation.util.ControllerUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final UserFacade userFacade;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationDto(jwt));
    }

    @PostMapping("/signup")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto signUpDto) {
        boolean isSuccessful = userFacade.registerUser(signUpDto);
        if (isSuccessful) {
            URI location = ControllerUtil.getLocation("/api/v1/user");
            return ResponseEntity.created(location).body(new ApiSignUpDto(
                    HttpStatus.CREATED.value(),
                    true,
                    "User registered successfully!"));
        } else {
            return ResponseEntity.badRequest()
                    .body(new ApiSignUpDto(
                            HttpStatus.BAD_REQUEST.value(),
                            false,
                            "Email is already taken!"));
        }
    }
}

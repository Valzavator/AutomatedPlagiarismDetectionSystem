package com.autoplag.presentation.payload.response;

import lombok.Data;

@Data
public class JwtAuthenticationDto {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationDto(String accessToken) {
        this.accessToken = accessToken;
    }
}

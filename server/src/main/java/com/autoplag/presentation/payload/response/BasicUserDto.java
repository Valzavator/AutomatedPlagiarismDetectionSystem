package com.autoplag.presentation.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicUserDto {
    private String username;
    private String email;
}

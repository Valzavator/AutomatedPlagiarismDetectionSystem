package com.gmail.maxsvynarchuk.presentation.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpDto {
    @NotBlank
    @Size(max = 32)
    private String firstName;

    @NotBlank
    @Size(max = 32)
    private String lastName;

    @NotBlank
    @Size(max = 255)
    @Email(regexp = "^(.+@.+\\..+)$")
    private String email;

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;
}

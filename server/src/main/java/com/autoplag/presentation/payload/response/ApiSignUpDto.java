package com.autoplag.presentation.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiSignUpDto {
    private Integer status;
    private Boolean success;
    private String message;
}

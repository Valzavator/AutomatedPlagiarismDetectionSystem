package com.autoplag.presentation.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDto {
    private Long id;
    private String fullName;
}

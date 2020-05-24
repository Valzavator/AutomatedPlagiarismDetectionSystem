package com.autoplag.presentation.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudentContainerDto {
    List<StudentDto> students;
}

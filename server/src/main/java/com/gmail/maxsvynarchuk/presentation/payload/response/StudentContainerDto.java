package com.gmail.maxsvynarchuk.presentation.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class StudentContainerDto {
    List<StudentDto> students;
}

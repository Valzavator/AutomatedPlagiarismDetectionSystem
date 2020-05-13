package com.gmail.maxsvynarchuk.presentation.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CourseDto {
    private Long id;
    private String name;
    private String description;
    private Date creationDate;
}

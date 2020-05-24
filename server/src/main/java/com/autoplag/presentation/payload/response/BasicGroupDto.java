package com.autoplag.presentation.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BasicGroupDto {
    private Long id;
    private String name;
    private Date creationDate;
}

package com.gmail.maxsvynarchuk.presentation.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDto {
    private Long id;

    private String name;
    private String repositoryPrefixPath;
    private String description;
}

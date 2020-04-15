package com.gmail.maxsvynarchuk.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepositoryFileInfo {
    private String path;

    private String url;

    private int size;
}

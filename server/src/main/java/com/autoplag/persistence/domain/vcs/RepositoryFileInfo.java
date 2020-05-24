package com.autoplag.persistence.domain.vcs;

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

package com.autoplag.persistence.domain.vcs;

import com.autoplag.persistence.domain.type.AuthorizationProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepositoryInfo {
    private AuthorizationProvider authorizationProvider;

    private String name;

    private String websiteUrl;

    private String apiUrl;

    private boolean isPrivate;

    private String prefixPath;

    private List<RepositoryFileInfo> filesInfo;

    public boolean isEmptyRepository() {
        return Objects.isNull(filesInfo) || filesInfo.isEmpty();
    }
}

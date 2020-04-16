package com.gmail.maxsvynarchuk.persistence.vcs.impl.github;

import com.gmail.maxsvynarchuk.config.constant.VCS;
import com.gmail.maxsvynarchuk.persistence.domain.AccessToken;
import com.gmail.maxsvynarchuk.persistence.domain.RepositoryFileInfo;
import com.gmail.maxsvynarchuk.persistence.domain.RepositoryInfo;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.InvalidVcsUrlException;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.OAuthIllegalTokenException;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsRepositoryDao;
import com.gmail.maxsvynarchuk.persistence.vcs.impl.github.dto.*;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Repository("vcsRepositoryGitHubDao")
@AllArgsConstructor
public class VcsRepositoryGitHubDao implements VcsRepositoryDao {
    public static final String AUTHORIZATION = "Authorization";
    public static final String ACCEPT = "Accept";
    private final Gson gson;

    @Override
    public RepositoryInfo getSubDirectoryRepositoryInfo(AccessToken accessToken,
                                                        String repositoryUrl,
                                                        String prefixPath,
                                                        Date lastCommitDate) {
        GitHubRepositoryInfo gitHubRepositoryInfo = getGitHubRepositoryInfo(accessToken, repositoryUrl);
        Optional<GitHubCommit> lastCommitOpt = getLastCommit(accessToken, gitHubRepositoryInfo, lastCommitDate);
        if (lastCommitOpt.isEmpty()) {
            return createRepositoryInfo(gitHubRepositoryInfo, List.of(), prefixPath);
        }
        GitHubTree tree = getTreeOfCommit(accessToken, lastCommitOpt.get());
        return createRepositoryInfo(gitHubRepositoryInfo, tree.getTree(), prefixPath);
    }

    @Override
    public String getRawFileContent(AccessToken accessToken, RepositoryFileInfo fileInfo) {
        return Unirest.get(fileInfo.getUrl())
                .header(AUTHORIZATION, accessToken.getAccessToken())
                .header(ACCEPT, VCS.GITHUB_API_RAW_ACCEPT_FORMAT)
                .asString()
                .getBody();
    }

    private RepositoryInfo createRepositoryInfo(GitHubRepositoryInfo gitHubRepositoryInfo,
                                                List<GitHubBlob> filesInfo,
                                                String prefixPath) {
        List<RepositoryFileInfo> filteredFilesInfo = filesInfo.stream()
                .filter(blob -> blob.getType().equals("blob")
                        && blob.getPath().startsWith(prefixPath)
                )
                .map(blob -> RepositoryFileInfo.builder()
                        .path(blob.getPath())
                        .url(blob.getUrl())
                        .size(blob.getSize())
                        .build()
                )
                .collect(Collectors.toList());

        return RepositoryInfo.builder()
                .authorizationProvider(AuthorizationProvider.GITHUB)
                .name(gitHubRepositoryInfo.getName())
                .apiUrl(gitHubRepositoryInfo.getApiUrl())
                .websiteUrl(gitHubRepositoryInfo.getWebsiteUrl())
                .isPrivate(gitHubRepositoryInfo.isPrivate())
                .prefixPath(prefixPath)
                .filesInfo(filteredFilesInfo)
                .build();
    }

    private GitHubRepositoryInfo getGitHubRepositoryInfo(AccessToken accessToken, String repositoryUrl) {
        String apiRepositoryUrl = getRepositoryUrl(repositoryUrl);
        return executeGetRequest(
                accessToken,
                apiRepositoryUrl,
                GitHubRepositoryInfo.class);
    }

    private Optional<GitHubCommit> getLastCommit(AccessToken accessToken,
                                                GitHubRepositoryInfo repositoryInfo,
                                                Date lastCommitDate) {
        String repositoryCommitsUrl = getRepositoryCommitsUrl(repositoryInfo.getApiUrl(), lastCommitDate);

        JSONArray jsonArray = Unirest.get(repositoryCommitsUrl)
                .header(AUTHORIZATION, accessToken.getAccessToken())
                .header(ACCEPT, VCS.GITHUB_API_JSON_ACCEPT_FORMAT)
                .asJson()
                .ifFailure(errorHandler())
                .getBody()
                .getArray();

        if (jsonArray.length() == 0) {
            return Optional.empty();
        }

        try {
            JSONObject jsonObject = jsonArray.getJSONObject(0)
                    .getJSONObject("commit");
            return Optional.of(
                    gson.fromJson(jsonObject.toString(), GitHubCommit.class));
        } catch (JSONException ex) {
            throw new InvalidVcsUrlException(ex);
        }
    }

    private GitHubTree getTreeOfCommit(AccessToken accessToken, GitHubCommit commit) {
        return executeGetRequest(
                accessToken,
                commit.getTree().getUrl() + "?recursive=true",
                GitHubTree.class);
    }

    private <T> T executeGetRequest(AccessToken accessToken,
                                    String url,
                                    Class<T> entityType) {
        HttpResponse<T> response = Unirest.get(url)
                .header(AUTHORIZATION, accessToken.getAccessToken())
                .header(ACCEPT, VCS.GITHUB_API_JSON_ACCEPT_FORMAT)
                .asObject(entityType)
                .ifFailure(errorHandler());

        return response.getBody();
    }

    private <T> Consumer<HttpResponse<T>> errorHandler() {
        return response -> {
            HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
            if (httpStatus.is2xxSuccessful()) {
                response.getParsingError().ifPresent(ex -> {
                    throw new InvalidVcsUrlException(ex);
                });
            }

            GitHubErrorResponse errorResponse = response.mapError(GitHubErrorResponse.class);
            if (Objects.nonNull(errorResponse)) {
                errorResponse.setStatus(response.getStatus());
                errorResponse.setStatusText(response.getStatusText());

                if (httpStatus == HttpStatus.UNAUTHORIZED) {
                    throw new OAuthIllegalTokenException(errorResponse.toString());
                } else {
                    throw new InvalidVcsUrlException(errorResponse.toString());
                }
            }
        };
    }

    private String getRepositoryUrl(String repositoryUrl) {
        return AuthorizationProvider.GITHUB.getApiRepositoryUrl(repositoryUrl);
    }

    private String getRepositoryCommitsUrl(String repositoryUrl, Date lastCommitDate) {
        repositoryUrl += VCS.GITHUB_API_COMMITS_SUFFIX_ENDPOINT + "?page=1&per_page=1";
        if (Objects.nonNull(lastCommitDate)) {
            repositoryUrl += "&until=" + lastCommitDate.toString();
        }
        return repositoryUrl;
    }

}

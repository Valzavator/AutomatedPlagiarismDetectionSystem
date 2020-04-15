package com.gmail.maxsvynarchuk.persistence.vcs.impl.github;

import com.gmail.maxsvynarchuk.config.constant.VCS;
import com.gmail.maxsvynarchuk.persistence.domain.AccessToken;
import com.gmail.maxsvynarchuk.persistence.domain.RepositoryFileInfo;
import com.gmail.maxsvynarchuk.persistence.domain.RepositoryInfo;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.InvalidVcsUrlException;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.OAuthIllegalTokenException;
import com.gmail.maxsvynarchuk.persistence.vcs.VcsRepositoryDao;
import com.gmail.maxsvynarchuk.persistence.vcs.impl.github.domain.*;
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
    private final Gson gson;

    @Override
    public RepositoryInfo getSubDirectoryRepositoryInfo(AccessToken accessToken,
                                                        String repositoryUrl,
                                                        String prefixPath,
                                                        Date lastCommitDate) {
        GitHubRepositoryInfo gitHubRepositoryInfo = getGitHubRepositoryInfo(accessToken, repositoryUrl);
        Optional<Commit> lastCommitOpt = getLastCommit(accessToken, gitHubRepositoryInfo, lastCommitDate);
        if (lastCommitOpt.isEmpty()) {
            return createRepositoryInfo(gitHubRepositoryInfo, List.of(), prefixPath);
        }
        Tree tree = getTreeOfCommit(accessToken, lastCommitOpt.get());
        return createRepositoryInfo(gitHubRepositoryInfo, tree.getTree(), prefixPath);
    }

    private RepositoryInfo createRepositoryInfo(GitHubRepositoryInfo gitHubRepositoryInfo,
                                                List<Blob> filesInfo,
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
                .name(gitHubRepositoryInfo.getName())
                .apiUrl(gitHubRepositoryInfo.getApiUrl())
                .websiteUrl(gitHubRepositoryInfo.getWebsiteUrl())
                .isPrivate(gitHubRepositoryInfo.isPrivate())
                .prefixPath(prefixPath)
                .filesInfo(filteredFilesInfo)
                .build();
    }

    @Override
    public String getRawFileContent(AccessToken accessToken, RepositoryFileInfo fileInfo) {
        return Unirest.get(fileInfo.getUrl())
                .header("Authorization", accessToken.getAccessToken())
                .header("Accept", VCS.GITHUB_API_RAW_ACCEPT_FORMAT)
                .asString()
                .getBody();
    }

    private GitHubRepositoryInfo getGitHubRepositoryInfo(AccessToken accessToken, String repositoryUrl) {
        String apiRepositoryUrl = getApiRepositoryUrl(repositoryUrl);
        return executeGetRequest(
                accessToken,
                apiRepositoryUrl,
                GitHubRepositoryInfo.class);
    }

    public Optional<Commit> getLastCommit(AccessToken accessToken,
                                          GitHubRepositoryInfo repositoryInfo,
                                          Date lastCommitDate) {
        String repositoryCommitsUrl = getRepositoryCommitsURL(repositoryInfo.getApiUrl(), lastCommitDate);

        JSONArray jsonArray = Unirest.get(repositoryCommitsUrl)
                .header("Authorization", accessToken.getAccessToken())
                .header("Accept", VCS.GITHUB_API_JSON_ACCEPT_FORMAT)
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
                    gson.fromJson(jsonObject.toString(), Commit.class));
        } catch (JSONException ex) {
            throw new InvalidVcsUrlException(ex);
        }
    }

    public Tree getTreeOfCommit(AccessToken accessToken, Commit commit) {
        return executeGetRequest(
                accessToken,
                commit.getTree().getUrl() + "?recursive=true",
                Tree.class);
    }

    private <T> T executeGetRequest(AccessToken accessToken,
                                    String url,
                                    Class<T> entityType) {
        HttpResponse<T> response = Unirest.get(url)
                .header("Authorization", accessToken.getAccessToken())
                .header("Accept", VCS.GITHUB_API_JSON_ACCEPT_FORMAT)
                .asObject(entityType)
                .ifFailure(errorHandler());

        return response.getBody();
    }

    private <T> Consumer<HttpResponse<T>> errorHandler() {
        return response -> {
            ErrorResponse errorResponse = response.mapError(ErrorResponse.class);

            if (Objects.nonNull(errorResponse)) {
                errorResponse.setStatus(response.getStatus());
                errorResponse.setStatusText(response.getStatusText());

                if (response.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                    throw new OAuthIllegalTokenException(errorResponse.toString());
                } else {
                    throw new InvalidVcsUrlException(errorResponse.toString());
                }
            }

            response.getParsingError().ifPresent(ex -> {
                throw new InvalidVcsUrlException(ex);
            });
        };
    }

    private String getApiRepositoryUrl(String repositoryUrl) {
        if (Objects.isNull(repositoryUrl) || repositoryUrl.isBlank()) {
            throw new InvalidVcsUrlException();
        }

        if (repositoryUrl.startsWith(VCS.GITHUB_API_REPOSITORY_PREFIX_ENDPOINT)) {
            return repositoryUrl;
        } else if (repositoryUrl.startsWith(VCS.GITHUB_WEBSITE_REPOSITORY_PREFIX_ENDPOINT)) {
            return repositoryUrl.replaceFirst(
                    VCS.GITHUB_WEBSITE_REPOSITORY_PREFIX_ENDPOINT,
                    VCS.GITHUB_API_REPOSITORY_PREFIX_ENDPOINT);
        }

        throw new InvalidVcsUrlException();
    }

    private String getRepositoryCommitsURL(String repositoryUrl, Date lastCommitDate) {
        repositoryUrl += VCS.GITHUB_API_COMMITS_SUFFIX_ENDPOINT + "?page=1&per_page=1";
        if (Objects.nonNull(lastCommitDate)) {
            repositoryUrl += "&until=" + lastCommitDate.toString();
        }
        return repositoryUrl;
    }

}

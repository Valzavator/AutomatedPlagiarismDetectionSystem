package com.autoplag.persistence.vcs.impl.bitbucket;

import com.autoplag.config.constant.VCS;
import com.autoplag.persistence.domain.type.AuthorizationProvider;
import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.persistence.domain.vcs.RepositoryFileInfo;
import com.autoplag.persistence.domain.vcs.RepositoryInfo;
import com.autoplag.persistence.exception.oauth.InvalidVcsUrlException;
import com.autoplag.persistence.exception.oauth.OAuthIllegalTokenException;
import com.autoplag.persistence.vcs.VcsRepositoryDao;
import com.autoplag.persistence.vcs.impl.bitbucket.dto.*;
import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Repository("vcsRepositoryBitbucketDao")
@AllArgsConstructor
public class VcsRepositoryBitbucketDao implements VcsRepositoryDao {
    public static final String AUTHORIZATION = "Authorization";
    private final static String REPOSITORY_INFO_FIELDS_PARAM =
            "?fields=name,is_private,links.self,links.source,links.html";
    private final static String COMMITS_INFO_FIELDS_PARAM =
            "?pagelen=50&fields=values.date,values.hash,next";
    private final static String BLOB_INFO_FIELDS_PARAM =
            "?pagelen=50&fields=values.path,values.type,values.links,values.size,next";

    private final VcsOAuthBitbucketDao vcsOAuthBitbucketDao;

    @Override
    public boolean checkAccess(AccessToken accessToken, String repositoryUrl) {
        try {
            return Objects.nonNull(getBitbucketRepositoryInfo(accessToken, repositoryUrl));
        } catch (OAuthIllegalTokenException ex) {
            accessToken = vcsOAuthBitbucketDao.getRefreshedOAuthToken(accessToken);
            return Objects.nonNull(getBitbucketRepositoryInfo(accessToken, repositoryUrl));
        }
    }

    @Override
    public RepositoryInfo getSubDirectoryRepositoryInfo(AccessToken accessToken,
                                                        String repositoryUrl,
                                                        String prefixPath,
                                                        Date lastCommitDate) {
        BitbucketRepositoryInfo bitbucketRepositoryInfo = getBitbucketRepositoryInfo(accessToken, repositoryUrl);
        Optional<BitbucketCommit> lastCommitOpt = getLastCommit(
                accessToken,
                bitbucketRepositoryInfo,
                prefixPath,
                lastCommitDate);
        if (lastCommitOpt.isEmpty()) {
            return createRepositoryInfo(bitbucketRepositoryInfo, List.of(), prefixPath);
        }
        List<BitbucketBlob> filesInfo = getFilesInfo(
                accessToken,
                bitbucketRepositoryInfo,
                lastCommitOpt.get(),
                prefixPath);
        return createRepositoryInfo(bitbucketRepositoryInfo, filesInfo, prefixPath);
    }

    @Override
    public String getRawFileContent(AccessToken accessToken, RepositoryFileInfo fileInfo) {
        return Unirest.get(fileInfo.getUrl())
                .header(AUTHORIZATION, accessToken.getAccessToken())
                .asString()
                .getBody();
    }

    private RepositoryInfo createRepositoryInfo(BitbucketRepositoryInfo bitbucketRepositoryInfo,
                                                List<BitbucketBlob> filesInfo,
                                                String prefixPath) {
        List<RepositoryFileInfo> filteredFilesInfo = filesInfo.stream()
                .filter(blob -> blob.getType().equals("commit_file")
                        && blob.getPath().startsWith(prefixPath)
                )
                .map(blob -> RepositoryFileInfo.builder()
                        .path(blob.getPath())
                        .url(blob.getSelfUrl())
                        .size(blob.getSize())
                        .build()
                )
                .collect(Collectors.toList());

        return RepositoryInfo.builder()
                .authorizationProvider(AuthorizationProvider.BITBUCKET)
                .name(bitbucketRepositoryInfo.getName())
                .apiUrl(bitbucketRepositoryInfo.getApiUrl())
                .websiteUrl(bitbucketRepositoryInfo.getWebsiteUrl())
                .isPrivate(bitbucketRepositoryInfo.isPrivate())
                .prefixPath(prefixPath)
                .filesInfo(filteredFilesInfo)
                .build();
    }

    private BitbucketRepositoryInfo getBitbucketRepositoryInfo(AccessToken accessToken, String repositoryUrl) {
        String apiRepositoryUrl = getRepositoryUrl(repositoryUrl);
        return Unirest.get(apiRepositoryUrl)
                .header(AUTHORIZATION, accessToken.getAccessToken())
                .asObject(BitbucketRepositoryInfo.class)
                .ifFailure(errorHandler(accessToken))
                .getBody();
    }

    private Optional<BitbucketCommit> getLastCommit(AccessToken accessToken,
                                                    BitbucketRepositoryInfo repositoryInfo,
                                                    String prefixPath,
                                                    Date lastCommitDate) {
        String repositoryCommitsUrl = getRepositoryCommitsUrl(repositoryInfo.getApiUrl(), prefixPath);
        BitbucketPagination<BitbucketCommit> commits = BitbucketPagination.<BitbucketCommit>builder()
                .next(repositoryCommitsUrl)
                .build();
        Optional<BitbucketCommit> lastCommit = Optional.empty();

        while (Objects.nonNull(commits.getNext())) {
            commits = Unirest.get(commits.getNext())
                    .header(AUTHORIZATION, accessToken.getAccessToken())
                    .asObject(new GenericType<BitbucketPagination<BitbucketCommit>>() {
                    })
                    .ifFailure(errorHandler(accessToken))
                    .getBody();

            lastCommit = commits.getValues().stream()
                    .filter(c -> c.getDate().compareTo(lastCommitDate) <= 0)
                    .max(Comparator.comparing(BitbucketCommit::getDate));
        }

        return lastCommit;
    }

    private List<BitbucketBlob> getFilesInfo(AccessToken accessToken,
                                             BitbucketRepositoryInfo bitbucketRepositoryInfo,
                                             BitbucketCommit commit,
                                             String prefixPath) {
        String repositorySrcUrl = bitbucketRepositoryInfo.getSourceUrl() + "/" +
                commit.getHash() + "/" + prefixPath + BLOB_INFO_FIELDS_PARAM;
        return getSubDirFilesInfo(accessToken, repositorySrcUrl);
    }

    private List<BitbucketBlob> getSubDirFilesInfo(AccessToken accessToken, String srcUrl) {
        BitbucketPagination<BitbucketBlob> filesInfoPage = BitbucketPagination.<BitbucketBlob>builder()
                .next(srcUrl)
                .build();
        List<BitbucketBlob> filesInfo = new ArrayList<>();

        while (Objects.nonNull(filesInfoPage.getNext())) {
            filesInfoPage = Unirest.get(filesInfoPage.getNext())
                    .header(AUTHORIZATION, accessToken.getAccessToken())
                    .asObject(new GenericType<BitbucketPagination<BitbucketBlob>>() {
                    })
                    .ifFailure(errorHandler(accessToken))
                    .getBody();
            filesInfo.addAll(filesInfoPage.getValues());
        }

        List<BitbucketBlob> toReturn = new ArrayList<>(filesInfo);
        for (BitbucketBlob blob : filesInfo) {
            if (blob.getType().equals("commit_directory")) {
                List<BitbucketBlob> subDirFilesInfo =
                        getSubDirFilesInfo(accessToken, blob.getSelfUrl() + BLOB_INFO_FIELDS_PARAM);
                toReturn.addAll(subDirFilesInfo);
            }
        }

        return toReturn;
    }

    private <T> Consumer<HttpResponse<T>> errorHandler(AccessToken accessToken) {
        return response -> {
            HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
            if (httpStatus.is2xxSuccessful()) {
                response.getParsingError().ifPresent(ex -> {
                    throw new InvalidVcsUrlException(ex);
                });
            }

            if (httpStatus == HttpStatus.FORBIDDEN) {
                BitbucketErrorResponse errorResponse = new BitbucketErrorResponse();
                errorResponse.setStatus(response.getStatus());
                errorResponse.setStatusText(response.getStatusText());
                errorResponse.setError("Access denied. You must have write or admin access.");
                throw new OAuthIllegalTokenException(errorResponse.toString(), accessToken);
            }

            BitbucketErrorResponse errorResponse = response.mapError(BitbucketErrorResponse.class);
            if (Objects.nonNull(errorResponse)) {
                errorResponse.setStatus(response.getStatus());
                errorResponse.setStatusText(response.getStatusText());

                if (httpStatus == HttpStatus.UNAUTHORIZED) {
                    throw new OAuthIllegalTokenException(errorResponse.toString(), accessToken);
                }
                throw new InvalidVcsUrlException(errorResponse.toString());
            }
        };
    }

    private String getRepositoryUrl(String repositoryUrl) {
        return AuthorizationProvider.BITBUCKET.getApiRepositoryUrl(repositoryUrl)
                + REPOSITORY_INFO_FIELDS_PARAM;
    }

    private String getRepositoryCommitsUrl(String repositoryUrl, String prefixPath) {
        repositoryUrl += VCS.GITHUB_API_COMMITS_SUFFIX_ENDPOINT + COMMITS_INFO_FIELDS_PARAM;
        if (Objects.nonNull(prefixPath) && !prefixPath.isBlank()) {
            repositoryUrl += "&path=" + prefixPath;
        }
        return repositoryUrl;
    }
}

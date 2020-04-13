package com.gmail.maxsvynarchuk.service.vcs.impl.github;

import com.gmail.maxsvynarchuk.config.constant.VCS;
import com.gmail.maxsvynarchuk.service.exception.InvalidVcsUrlException;
import com.gmail.maxsvynarchuk.service.vcs.VcsDownloadRepositoryService;
import com.gmail.maxsvynarchuk.service.vcs.domain.Repository;
import com.gmail.maxsvynarchuk.service.vcs.impl.github.domain.*;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class VcsDownloadRepositoryServiceImpl implements VcsDownloadRepositoryService {
    private Gson gson;

    public VcsDownloadRepositoryServiceImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public RepositoryInfo downloadRepositoryInfo(String accessToken, String repositoryUrl) {
        String apiRepositoryUrl = getApiRepositoryUrl(repositoryUrl);
        return executeGetRequest(
                accessToken,
                apiRepositoryUrl,
                RepositoryInfo.class);
    }

    @Override
    public Optional<Commit> downloadLastCommit(String accessToken,
                                               RepositoryInfo repositoryInfo,
                                               Date until) {
        String repositoryCommitsUrl = getRepositoryCommitsURL(repositoryInfo.getApiUrl(), until);

        JSONArray jsonArray = Unirest.get(repositoryCommitsUrl)
                .header("Authorization", accessToken)
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

    @Override
    public Tree downloadTreeOfCommit(String accessToken, Commit commit) {
        Tree tree = executeGetRequest(
                accessToken,
                commit.getTree().getUrl() + "?recursive=true",
                Tree.class);

        List<Blob> blobs = tree.getTree()
                .stream()
                .filter(blob -> blob.getType().equals("blob"))
                .collect(Collectors.toList());
        tree.setTree(blobs);

        return tree;
    }

    @Override
    public String downloadContentOfBlob(String accessToken, Blob blob) {
        return Unirest.get(blob.getUrl())
                .header("Authorization", accessToken)
                .header("Accept", VCS.GITHUB_API_RAW_ACCEPT_FORMAT)
                .asString()
                .getBody();
    }

    @Override
    public Repository downloadSubDirectoryFromRepository(String accessToken,
                                                         String repositoryUrl,
                                                         String prefixPath,
                                                         Date lastCommitDate) {
        return null;
    }

    public void downloadRepository(
            String accessToken,
            String repositoryURL,
            String prefixPath,
            Date lastCommitDate) {


//        try {
//
//            if (!repositoryName.isEmpty()) {
//                Files.walk(Path.of(DEFAULT_DATA_FOLDER + repositoryName))
//                        .sorted(Comparator.reverseOrder())
//                        .forEach(path -> {
//                            try {
//                                Files.delete(path);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        });
//
//            }
//
//            for (TreeEntity treeEntity : tree.getTree()) {
//                if (!treeEntity.getPath().startsWith(prefixPath)) {
//                    continue;
//                }
//
//                Path file = Paths.get(DEFAULT_DATA_FOLDER + repositoryName + treeEntity.getPath());
//                if (treeEntity.getType().equals("blob")) {
//                    Files.createDirectories(file.getParent());
//                    Files.createFile(file);
//
//                    String string = Unirest.get(treeEntity.getUrl())
//                            .header("Authorization", accessToken)
//                            .header("Accept", "application/vnd.github.3.raw")
//                            .asString()
//                            .getBody();
//
//                    try (BufferedWriter writer = Files.newBufferedWriter(file)) {
//                        writer.write(string, 0, string.length());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    private <T> T executeGetRequest(String accessToken,
                                    String url,
                                    Class<T> entityType) {
        HttpResponse<T> response = Unirest.get(url)
                .header("Authorization", accessToken)
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
                throw new InvalidVcsUrlException(errorResponse.toString());
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

    private String getRepositoryCommitsURL(String repositoryUrl, Date until) {
        repositoryUrl += VCS.GITHUB_API_COMMITS_SUFFIX_ENDPOINT + "?page=1&per_page=1";
        if (Objects.nonNull(until)) {
            repositoryUrl += "&until=" + until.toString();
        }
        return repositoryUrl;
    }

}

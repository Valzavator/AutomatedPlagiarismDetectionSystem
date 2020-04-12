package com.gmail.maxsvynarchuk.service.vcs.impl.github;

import com.gmail.maxsvynarchuk.service.vcs.VCSDownloadRepositoryService;
import com.gmail.maxsvynarchuk.service.vcs.impl.github.entity.Commit;
import com.gmail.maxsvynarchuk.service.vcs.impl.github.entity.Tree;
import com.gmail.maxsvynarchuk.service.vcs.impl.github.entity.TreeEntity;
import com.google.gson.Gson;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

@Service
public class VCSDownloadRepositoryServiceImpl implements VCSDownloadRepositoryService {
    private Gson gson;

    public VCSDownloadRepositoryServiceImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void downloadRepository(String accessToken, String repositoryURL, String prefixPath, Date lastCommitDate) {
//        validateParameters(accessToken, repositoryURL);

        // TODO - get repository name
        String repositoryName = "test_github_api\\";

        String repositoryCommitsURL = configureRepositoryCommitsURL(repositoryURL, lastCommitDate);

        JSONObject jsonNodeResponse = Unirest.get(repositoryCommitsURL)
                .header("Authorization", accessToken)
                .header("Accept", "application/vnd.github.v3+json")
                .asJson()
                .getBody()
                .getArray()
                .getJSONObject(0)
                .getJSONObject("commit");

        Commit commitResponse = gson.fromJson(jsonNodeResponse.toString(), Commit.class);

        System.out.println(commitResponse);

        Tree tree = Unirest.get(commitResponse.getTree().getUrl() + "?recursive=true")
                .header("Authorization", accessToken)
                .header("Accept", "application/vnd.github.v3+json")
                .asObject(Tree.class)
                .getBody();

        System.out.println(tree.toString());

        try {

            if (!repositoryName.isEmpty()) {
                Files.walk(Path.of(DEFAULT_DATA_FOLDER + repositoryName))
                        .sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

            }

            for (TreeEntity treeEntity : tree.getTree()) {
                if (!treeEntity.getPath().startsWith(prefixPath)) {
                    continue;
                }

                Path file = Paths.get(DEFAULT_DATA_FOLDER + repositoryName + treeEntity.getPath());
                if (treeEntity.getType().equals("blob")) {
                    Files.createDirectories(file.getParent());
                    Files.createFile(file);

                    String string = Unirest.get(treeEntity.getUrl())
                            .header("Authorization", accessToken)
                            .header("Accept", "application/vnd.github.3.raw")
                            .asString()
                            .getBody();

                    try (BufferedWriter writer = Files.newBufferedWriter(file)) {
                        writer.write(string, 0, string.length());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String configureRepositoryCommitsURL(String repositoryURL, Date lastCommitDate) {
        repositoryURL += "/commits?page=1&per_page=1";
        if (Objects.nonNull(lastCommitDate)) {
            repositoryURL += "&until" + lastCommitDate.toString();
        }
        return repositoryURL;
    }

    // TODO
    private void validateParameters(String accessToken, String repositoryURL, String prefixPath, Date lastCommitDate) {

    }
}

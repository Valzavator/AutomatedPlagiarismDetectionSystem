package com.gmail.maxsvynarchuk.service.vcs.impl.github.entity;

// https://api.github.com/repos/Muguvara/test_github_api/commits?until=2020-05-11T00:00:00Z&per_page=1
// https://api.github.com/repos/Muguvara/test_github_api/git/trees/f255bd3c6d1c7fa08f6475ed14de4441d2d77427?recursive=true
// https://api.github.com/repos/Muguvara/test_github_api/git/blobs/87c86a35909aebcca5813444779350abc473a926 with Accept: application/vnd.github.3.raw

import kong.unirest.Unirest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepositoryManager {
    public static void main(String[] args) throws IOException {
//        Path file = Paths.get("api/resource/directory/test.txt");
//
//        try {
//            Files.createDirectories(file.getParent());
//            Files.createFile(file);
//
//            String s = "asdasdasdasd";
//            try (BufferedWriter writer = Files.newBufferedWriter(file)) {
//                writer.write(s, 0, s.length());
//            } catch (IOException x) {
//                System.err.format("IOException: %s%n", x);
//            }
//
//        } catch (FileAlreadyExistsException x) {
//            System.err.format("file named %s" +
//                    " already exists%n", file);
//        } catch (IOException x) {
//            // Some other sort of failure, such as permissions.
//            System.err.format("createFile error: %s%n", x);
//        }

        RepositoryManager rm = new RepositoryManager();
        rm.getCommit();
    }

    public void getCommit() {
//        String commits = Unirest.get("https://api.github.com/repos/Muguvara/test_github_api/commits?until=2020-05-11T00:00:00Z&per_page=1")
//                .header("Authorization", "Bearer " + "da6e938d898a8015a33c994e2e9dcc16fe3b2f29")
//                .header("Accept", "application/vnd.github.v3+json")
//                .asString()
//                .getBody();

//        CommitResponse commitResponse = Unirest.get("https://api.github.com/repos/Muguvara/test_github_api/commits/387ee8bf69aff979e57a0a5f6e1247982afc66e4")
//                .header("Authorization", "Bearer " + "da6e938d898a8015a33c994e2e9dcc16fe3b2f29")
//                .header("Accept", "application/vnd.github.v3+json")
//                .asObject(CommitResponse.class)
//                .getBody();
//
//        System.out.println(commitResponse.toString());
//        Unirest.shutDown();
//
//        Tree tree = Unirest.get(commitResponse.getCommit().getTree().getUrl() + "?recursive=true")
//                .header("Authorization", "Bearer " + "da6e938d898a8015a33c994e2e9dcc16fe3b2f29")
//                .header("Accept", "application/vnd.github.v3+json")
//                .asObject(Tree.class)
//                .getBody();
//
//        System.out.println(tree.toString());
//
//        String repositoryRoot = "github/api/";
//        for (TreeEntity treeEntity : tree.getTree()) {
//            if (!treeEntity.getPath().startsWith("common/exceptions")) {
//                continue;
//            }
//            if (treeEntity.getType().equals("tree")) {
//                Path file = Paths.get(repositoryRoot + treeEntity.getPath());
//                try {
//                    Files.createDirectories(file);
////                    System.out.println("\n\nCREATE " + file);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (treeEntity.getType().equals("blob")) {
//                Path file = Paths.get(repositoryRoot + treeEntity.getPath());
//                try {
//                    Files.deleteIfExists(file);
//                    Files.createFile(file);
//
//                    String string = Unirest.get(treeEntity.getUrl())
//                            .header("Authorization", "Bearer " + "da6e938d898a8015a33c994e2e9dcc16fe3b2f29")
//                            .header("Accept", "application/vnd.github.3.raw")
//                            .asString()
//                            .getBody();
//
//                    try (BufferedWriter writer = Files.newBufferedWriter(file)) {
//                        writer.write(string, 0, string.length());
//                    } catch (IOException x) {
//                        System.err.format("IOException: %s%n", x);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

}

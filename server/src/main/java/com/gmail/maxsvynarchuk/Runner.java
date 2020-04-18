package com.gmail.maxsvynarchuk;

import com.gmail.maxsvynarchuk.config.constant.JPlag;
import com.gmail.maxsvynarchuk.config.constant.Path;
import com.gmail.maxsvynarchuk.persistence.domain.*;
import com.gmail.maxsvynarchuk.persistence.plagiarism.jplag.SoftwarePlagDetectionToolImpl;
import com.gmail.maxsvynarchuk.util.ResourceManager;

import java.io.File;
import java.io.IOException;

public class Runner {
    public static final String githubUrl = "https://github.com/Muguvara/test_github_api";
    public static final String bitbucketUrl = "https://bitbucket.org/Muguvara/test_api_oauth/";

    public static final String githubToken = "119d33f6aaa20f3a7f2d397a76f433043b83dbb2";
    public static final String bitbucketToken =
            "PTAk_NPfAAgUiJy4f5iYpiZVsVOwSmeNU3lIcZ-WptuzIpsUBxvm3n4Uu-RAoCbmyI60z7kYhNJzsns6S3X6nLthlquhv7GBP8NFrzcTQ18UHtQJdvFlx0682tNPKX_nxfq-8ohOW-aj-YobDvcV";


    public static void main(String[] args) throws IOException, InterruptedException {
//        AccessToken accessToken = new AccessToken();
//        accessToken.setAccessTokenString(githubToken);
//        accessToken.setTokenType("Bearer");
//        System.out.println(accessToken);
////        VcsRepositoryDao vcsRepositoryDao = new VcsRepositoryBitbucketDao();
//        VcsRepositoryDao vcsRepositoryDao = new VcsRepositoryGitHubDao(new Gson());
//
//        RepositoryInfo repositoryInfo = vcsRepositoryDao
//                .getSubDirectoryRepositoryInfo(
//                        accessToken,
//                        "https://bitbucket.org/Valzavator/technologyofsoftwaredevelopment",
//                        "lab9/task1/src"
////                        new Date(117, 11, 22)
//                );
//
//        System.out.println(repositoryInfo);
//        System.out.println(
//                vcsRepositoryDao.getRawFileContent(accessToken, repositoryInfo.getFilesInfo().get(2)));

//        VcsDownloadToFileSystemService service = new VcsDownloadToFileSystemServiceImpl(
//                new VcsRepositoryBitbucketDao(),
//                new VcsRepositoryGitHubDao(new Gson()),
//                new FileSystemWriter(new CyrillicRemovalStrategy()));
//
//        service.downloadOneRepository(accessToken,
//                githubUrl,
//                "",
//                new Date(120, 4, 11, 23, 59));

//        String javaCmd = ProcessUtils.getJavaCmd().getAbsolutePath();

//        System.out.println(System.getProperty("java.version"));
//        System.out.println(System.getenv("JAVA_HOME"));
//        File file = new File("jplag");
//        ProcessBuilder builder = new ProcessBuilder( "\"C:\\Program Files\\Java\\jdk-12\\bin\\java\"", "", "--version");
//        ProcessBuilder builder = new ProcessBuilder("C:/Program Files/Java/jdk-12/bin/java",
////                System.getenv("JAVA_HOME") + "\\bin\\java",
//                "-jar",
//                "jplag\\jplag-2.12.1.jar",
//                "-s", "data/task1",
//                "-r", Path.ANALYSIS_RESULT_FOLDER,
//                "-l", "java19"
//        );

//        Process process = builder.inheritIO().start();

        //        process.waitFor();
//        System.out.println(process.waitFor());
//        System.out.println(process.exitValue());
//        // Then retreive the process output
//        InputStream in = process.getInputStream();
//        InputStream err = process.getErrorStream();

//        byte b[]=new byte[in.available()];
//        in.read(b,0,b.length);
//        System.out.println(new String(b));
//
//        byte c[]=new byte[err.available()];
//        err.read(c,0,c.length);
//        System.out.println(new String(c));

        PlagDetectionSetting setting = PlagDetectionSetting.builder()
                .programLanguage(new ProgramLanguage(null, "java19"))
//                .comparisonSensitivity(20)
//                .minimumSimilarityPercent(1)
//                .baseCodePath("data")
//                .typeDetection()
                .dataPath(Path.DATA_FOLDER + "/" + "task1")
                .resultPath(Path.ANALYSIS_RESULT_FOLDER + "/" + "task1")
                .build();
        SoftwarePlagDetectionToolImpl s = new SoftwarePlagDetectionToolImpl();

        PlagResult result = s.generateHtmlResult(setting);
        System.out.println(result);
    }
}

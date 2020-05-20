package com.gmail.maxsvynarchuk;

import com.gmail.maxsvynarchuk.persistence.domain.*;
import com.gmail.maxsvynarchuk.persistence.plagiarism.jplag.SoftwarePlagDetectionToolImpl;
import com.gmail.maxsvynarchuk.util.preprocessing.NonEnglishRemovalStrategy;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Runner {
    public static final String githubUrl = "https://github.com/Muguvara/test_github_api";
    public static final String bitbucketUrl = "https://bitbucket.org/Muguvara/test_api_oauth/";

    public static final String githubToken = "119d33f6aaa20f3a7f2d397a76f433043b83dbb2";
    public static final String bitbucketToken =
            "PTAk_NPfAAgUiJy4f5iYpiZVsVOwSmeNU3lIcZ-WptuzIpsUBxvm3n4Uu-RAoCbmyI60z7kYhNJzsns6S3X6nLthlquhv7GBP8NFrzcTQ18UHtQJdvFlx0682tNPKX_nxfq-8ohOW-aj-YobDvcV";


    public static void main(String[] args) throws IOException, InterruptedException {
//        String str = "Language accepted: Javac 1.9+ based AST plugin\nCommand line: -s C:\\Users\\Valzavator\\IdeaProjects\\AutomatedPlagiarismDetectionSystem\\server\\target\\classes\\data\\zip\\2020-05-07\\19-49-15\\source.zip -r C:\\Users\\Valzavator\\IdeaProjects\\AutomatedPlagiarismDetectionSystem\\server\\target\\classes\\static\\jplag-results\\2020-05-07\\19-49-15\\source.zip -l java19 -t 12 -m 10% \r\ninitialize ok\r\n0 submissions\r\n\n0 submissions parsed successfully!\n0 parser errors!\n\n\nError: Not enough valid submissions! (only 0 are valid):\n\r\n" +
//                 "Comparing progbase_sr3_sverhrazum.c-progbase_sr3_zverevfpm.c: 0.0\r\nComparing progbase_sr3_tiwatit.c-progbase_sr3_valerystudent.c: 0.0\r\nComparing progbase_sr3_tiwatit.c-progbase_sr3_wmdanor.c: 0.0\r\nComparing progbase_sr3_tiwatit.c-progbase_sr3_yehorf.c: 0.0\r\nComparing progbase_sr3_tiwatit.c-progbase_sr3_zverevfpm.c: 0.0\r\nComparing progbase_sr3_valerystudent.c-progbase_sr3_wmdanor.c: 23.030304\r\nComparing progbase_sr3_valerystudent.c-progbase_sr3_yehorf.c: 24.358974\r\nComparing progbase_sr3_valerystudent.c-progbase_sr3_zverevfpm.c: 0.0\r\nComparing progbase_sr3_wmdanor.c-progbase_sr3_yehorf.c: 30.76923\r\nComparing progbase_sr3_wmdanor.c-progbase_sr3_zverevfpm.c: 0.0\r\nComparing progbase_sr3_yehorf.c-progbase_sr3_zverevfpm.c: 0.0\r\n\nWriting results to: C:\\Users\\Valzavator\\IdeaProjects\\AutomatedPlagiarismDetectionSystem\\server\\target\\classes\\static\\jplag-results\\2020-05-07\\19-57-42\\source.zip\n";
//
//        int startIndex = str.indexOf("initialize ok");
//        int endIndex = str.indexOf("Writing results to:");
        System.out.println(Path.of("/asd/asdas").getParent().toString());

//        System.out.println(Charset.availableCharsets());
//        System.out.println(Charset.isSupported("ISO-8859-5"));
//        String st = "/classes/static/jplag-results/2020-04-24/00-09-06\\group1.zip";
////        String st1 = "C:\\Users\\Valzavator\\IdeaProjects\\AutomatedPlagiarismDetectionSystem\\server\\target\\classes\\static\\jplag-results\\";
////        Path p = Paths.get(st).normalize();
////        Path p1 = Paths.get(st1);
////        System.out.println(p1.getParent().relativize(p).toString());
//        System.out.println(new File(st).getPath());
////        AccessToken accessToken = new AccessToken();
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
        ProcessBuilder builder = new ProcessBuilder( "\"C:\\Program Files\\Java\\jdk-12\\bin\\java\"", "", "--version");
//        ProcessBuilder builder = new ProcessBuilder("C:/Program Files/Java/jdk-12/bin/java",
////                System.getenv("JAVA_HOME") + "\\bin\\java",
//                "-jar",
//                "jplag\\jplag-2.12.1.jar",
//                "-s", "data/task1",
//                "-r", Path.ANALYSIS_RESULT_FOLDER,
//                "-l", "java19"
//        );
        Process process = builder.start();
        process.waitFor();

        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(process.getInputStream());
        int charsRead;
        while((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
            out.append(buffer, 0, charsRead);
        }
//        System.out.println(out.toString());



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

//        PlagDetectionSetting setting = PlagDetectionSetting.builder()
//                .programmingLanguage(new ProgrammingLanguage(null, "java19"))
////                .comparisonSensitivity(20)
////                .minimumSimilarityPercent(1)
////                .baseCodePath("data")
////                .typeDetection()
//                .dataPath(Path.DATA_FOLDER + "/" + "task1")
//                .resultPath(Path.ANALYSIS_RESULT_FOLDER + "/" + "task1")
//                .build();
//        SoftwarePlagDetectionToolImpl s = new SoftwarePlagDetectionToolImpl();
//
//        PlagDetectionResult result = s.generateHtmlResult(setting);
//        System.out.println(result);

//
//        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
//
//        List<Future<Boolean>> futures = new ArrayList<>(5);

//        for (int i = 1; i <= 5; i++) {
//            RunTask task = new RunTask(String.valueOf(i));
//            futures.add(executor.submit(task));
//        }
//
//        executor.shutdown();
//
//        System.out.println("start executor");
//        if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
//            executor.shutdownNow();
//            if (!executor.awaitTermination(2000, TimeUnit.MILLISECONDS)) {
//                System.out.println("Pool did not terminate");
//            }
//        }
//        System.out.println("end executor");

//        futures.forEach(f -> {
////            try {
//            System.out.println("2. " + f.isDone());
//            System.out.println("canceled: " + f.cancel(true));
//            System.out.println("3. " + f.isCancelled());
//                if (f.isDone()) {
//                    try {
//                        System.out.println(f.get());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//                }
////                System.out.println("1. " + f.get());
////            } catch (InterruptedException | ExecutionException e) {
////                e.printStackTrace();
////            }
//
//        });
    }
}

//class RunTask implements Callable<Boolean> {
//    private String task;
//
//    public RunTask(String task) {
//        this.task = task;
//    }
//
//    @Override
//    public Boolean call() throws Exception {
//        System.out.println("Start task " + task);
//        for (int i = 0; i < 10; i++) {
//            Thread.sleep(800);
//        }
//        System.out.println("End task " + task);
//        return true;
//    }
//}

package com.gmail.maxsvynarchuk.persistence.plagiarism.jplag;

import com.gmail.maxsvynarchuk.config.constant.JPlag;
import com.gmail.maxsvynarchuk.config.constant.Paths;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSettings;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.persistence.plagiarism.SoftwarePlagDetectionTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class SoftwarePlagDetectionToolImpl implements SoftwarePlagDetectionTool {
    public static final String INDEX_FILE = "index.html";
    public static final String BAD_BASECODE_ERROR = "Error: Bad basecode submission";

    @Override
    public PlagDetectionResult generateHtmlResult(PlagDetectionSettings settings) {
        if (!validateSettings(settings)) {
            return PlagDetectionResult.failed("Invalid settings");
        }
        String jplagLog = null;
        ProcessBuilder processBuilder = configureJplagProcess(settings);
        boolean isSuccessful = false;
        try {
            Process process;
            if (settings.getSaveLog()) {
                process = processBuilder.start();
                jplagLog = getJPlagLog(process.getInputStream());
            } else {
                process = processBuilder.inheritIO().start();
            }
            isSuccessful = process.waitFor() == 0;
        } catch (IOException | InterruptedException ex) {
            log.error(ex.toString());
        }
        PlagDetectionResult result = PlagDetectionResult.builder()
                .date(new Date())
                .isSuccessful(isSuccessful)
                .log(jplagLog)
                .build();
        if (isSuccessful) {
            result.setResultMessage("Plagiarism detection tool successfully done analysis!");
            result.setResultPath(
                    generateResultPath(settings.getResultPath()));
        } else {
            if (Objects.nonNull(jplagLog) && jplagLog.contains(BAD_BASECODE_ERROR)) {
                result.setResultMessage("Archive with BASECODE must contain files with allowed format " +
                        "for the selected programming language!");
            } else {
                result.setResultMessage("Plagiarism detection tool was interrupted (see logs)!");
            }
        }
        return result;
    }

    private ProcessBuilder configureJplagProcess(PlagDetectionSettings setting) {
        ProcessBuilder builder = new ProcessBuilder();
        List<String> commands = new LinkedList<>();
        // execute jar file
        commands.add(JPlag.JAVA_PATH);
        commands.add("-jar");
        commands.add(JPlag.JPlAG_PATH);

        // data directory path
        commands.add("-s");
        commands.add(setting.getDataPath());

        // result directory path
        commands.add("-r");
        commands.add(setting.getResultPath());

        // programming language type
        commands.add("-l");
        commands.add(setting.getProgrammingLanguage().getName());

        // sensitivity of the comparison
        if (Objects.nonNull(setting.getComparisonSensitivity())) {
            commands.add("-t");
            commands.add(setting.getComparisonSensitivity().toString());
        }

        // min similarity percent
        if (Objects.nonNull(setting.getMinimumSimilarityPercent())) {
            commands.add("-m");
            commands.add(setting.getMinimumSimilarityPercent().toString() + "%");
        }

        // base code directory path
        if (Objects.nonNull(setting.getBaseCodePath())) {
            commands.add("-bc");
            commands.add(JPlag.BASE_CODE_DIRECTORY);
        }

        return builder.command(commands);
    }

    private boolean validateSettings(PlagDetectionSettings setting) {
        return Objects.nonNull(setting) &&
                Objects.nonNull(setting.getDataPath()) &&
                Objects.nonNull(setting.getResultPath()) &&
                Objects.nonNull(setting.getProgrammingLanguage());
    }

    private String generateResultPath(String settingResultPathStr) {
        Path settingResultPath = Path.of(settingResultPathStr);
        Path staticFolderPath = Path.of(Paths.STATIC_FOLDER);
        String urlPath = staticFolderPath.relativize(settingResultPath)
                .toString()
                .replace("\\", "/");
        return Paths.URL_HOST + "/" + urlPath + "/" + INDEX_FILE;
    }

    private String getJPlagLog(InputStream stream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        while ((length = stream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return removeSensitiveData(result.toString(StandardCharsets.UTF_8));
    }

    private String removeSensitiveData(String data) {
        int startIndex = data.indexOf("initialize ok");
        int endIndex = data.indexOf("Writing results to:");
        if (startIndex == -1) {
            return data;
        }
        if (endIndex == -1) {
            return data.substring(startIndex);
        }
        return data.substring(startIndex, endIndex);
    }

}

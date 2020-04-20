package com.gmail.maxsvynarchuk.persistence.plagiarism.jplag;

import com.gmail.maxsvynarchuk.config.constant.JPlag;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSetting;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.persistence.plagiarism.SoftwarePlagDetectionTool;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SoftwarePlagDetectionToolImpl implements SoftwarePlagDetectionTool {

    @Override
    public PlagDetectionResult generateHtmlResult(PlagDetectionSetting configuration) {
        ProcessBuilder processBuilder = configureJplagProcess(configuration);
        boolean isSuccessful = false;
        try {
            Process process = processBuilder.inheritIO().start();
            isSuccessful = process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        PlagDetectionResult result = PlagDetectionResult.builder()
                .date(new Date())
                .taskGroup(configuration.getTaskGroup())
                .isSuccessful(isSuccessful)
                .build();
        if (isSuccessful) {
            result.setResultPath(configuration.getResultPath());
        }
        return result;
    }

    private ProcessBuilder configureJplagProcess(PlagDetectionSetting configuration) {
        ProcessBuilder builder = new ProcessBuilder();
        List<String> commands = new LinkedList<>();
        // execute jar file
        commands.add(JPlag.JAVA_PATH);
        commands.add("-jar");
        commands.add(JPlag.JPlAG_PATH);

        // data directory path
        commands.add("-s");
        commands.add(configuration.getDataPath());

        // result directory path
        commands.add("-r");
        commands.add(configuration.getResultPath());

        // programming language type
        commands.add("-l");
        commands.add(configuration.getProgramLanguage().getName());

        // sensitivity of the comparison
        if (Objects.nonNull(configuration.getComparisonSensitivity())) {
            commands.add("-t");
            commands.add(configuration.getComparisonSensitivity().toString());
        }

        // min similarity percent
        if (Objects.nonNull(configuration.getMinimumSimilarityPercent())) {
            commands.add("-m");
            commands.add(configuration.getMinimumSimilarityPercent().toString() + "%");
        }

        // base code directory path
        if (Objects.nonNull(configuration.getBaseCodePath())) {
            commands.add("-bc");
            commands.add(configuration.getBaseCodePath());
        }

        return builder.command(commands);
    }

}

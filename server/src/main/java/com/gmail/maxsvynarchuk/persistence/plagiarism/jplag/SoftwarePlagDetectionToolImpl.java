package com.gmail.maxsvynarchuk.persistence.plagiarism.jplag;

import com.gmail.maxsvynarchuk.config.constant.JPlag;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSetting;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.persistence.plagiarism.SoftwarePlagDetectionTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class SoftwarePlagDetectionToolImpl implements SoftwarePlagDetectionTool {

    @Override
    public PlagDetectionResult generateHtmlResult(PlagDetectionSetting setting) {
        ProcessBuilder processBuilder = configureJplagProcess(setting);
        boolean isSuccessful = false;
        try {
            Process process = processBuilder.inheritIO().start();
            isSuccessful = process.waitFor() == 0;
        } catch (IOException | InterruptedException ex) {
            log.error(ex.toString());
        }

        PlagDetectionResult result = PlagDetectionResult.builder()
                .date(new Date())
//                .taskGroup(configuration.getTaskGroup())
                .isSuccessful(isSuccessful)
                .build();
        if (isSuccessful) {
            result.setResultPath(setting.getResultPath());
        } else {
            result.setMessage("Plagiarism detection tool was interrupted!");
        }

        return result;
    }

    private ProcessBuilder configureJplagProcess(PlagDetectionSetting setting) {
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
            commands.add(setting.getBaseCodePath());
        }

        return builder.command(commands);
    }

}

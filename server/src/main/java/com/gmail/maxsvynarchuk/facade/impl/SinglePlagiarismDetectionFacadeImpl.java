package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.config.constant.Paths;
import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.SinglePlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSetting;
import com.gmail.maxsvynarchuk.presentation.payload.request.PlagDetectionSettingDto;
import com.gmail.maxsvynarchuk.service.PlagDetectionResultService;
import com.gmail.maxsvynarchuk.service.PlagiarismDetectionService;
import com.gmail.maxsvynarchuk.util.FileSystemWriter;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Facade
@AllArgsConstructor
public class SinglePlagiarismDetectionFacadeImpl implements SinglePlagiarismDetectionFacade {
    private final Converter<PlagDetectionSettingDto, PlagDetectionSetting> converter;
    private final PlagiarismDetectionService plagiarismDetectionService;
    private final PlagDetectionResultService plagDetectionResultService;
    private final FileSystemWriter fileSystemWriter;

    @Override
    public PlagDetectionResult processForZipFile(PlagDetectionSettingDto settingDto, MultipartFile multipartFile) {
        PlagDetectionSetting setting = converter.convert(settingDto);
        generatePaths(setting, multipartFile);
        if (!fileSystemWriter.unzipFile(multipartFile, setting.getDataPath())) {
            return PlagDetectionResult.failed("Unable to unpack archive!");
        }
        PlagDetectionResult result = plagiarismDetectionService.processForSingleTask(setting);
        return plagDetectionResultService.savePlagDetectionResult(result);
    }

    private void generatePaths(PlagDetectionSetting setting, MultipartFile multipartFile) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd" + File.separator + "HH-mm-ss");
        setting.setDataPath(
                Path.of(Paths.ZIP_DATA_FOLDER,
                        formatter.format(date),
                        multipartFile.getOriginalFilename()).toString());
        setting.setResultPath(
                Path.of(Paths.ANALYSIS_RESULT_FOLDER,
                        formatter.format(date),
                        multipartFile.getOriginalFilename()).toString());
    }

}

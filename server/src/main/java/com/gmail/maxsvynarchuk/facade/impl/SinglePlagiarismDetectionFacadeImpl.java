package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.config.constant.JPlag;
import com.gmail.maxsvynarchuk.config.constant.Paths;
import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.SinglePlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSettings;
import com.gmail.maxsvynarchuk.persistence.domain.ProgrammingLanguage;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionDto;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionResultDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSingleCheckSettingsDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.ProgrammingLanguageDto;
import com.gmail.maxsvynarchuk.service.PlagDetectionResultService;
import com.gmail.maxsvynarchuk.service.PlagiarismDetectionService;
import com.gmail.maxsvynarchuk.service.ProgrammingLanguageService;
import com.gmail.maxsvynarchuk.util.FileSystemWriter;
import lombok.AllArgsConstructor;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Facade
@AllArgsConstructor
public class SinglePlagiarismDetectionFacadeImpl implements SinglePlagiarismDetectionFacade {
    public static final String DATE_FORMAT = "yyyy-MM-dd" + File.separator + "HH-mm-ss";

    private final ProgrammingLanguageService programmingLanguageService;
    private final PlagiarismDetectionService plagiarismDetectionService;
    private final PlagDetectionResultService plagDetectionResultService;

    private final FileSystemWriter fileSystemWriter;
    private final Converter<ProgrammingLanguage, ProgrammingLanguageDto> languageConverter;
    private final Converter<SingleCheckPlagDetectionDto, PlagDetectionSettings> settingsConverter;
    private final Converter<PlagDetectionResult, SingleCheckPlagDetectionResultDto> resultConverter;

    @Override
    public OptionsForSingleCheckSettingsDto getOptionsForSingleCheckSettings() {
        List<ProgrammingLanguage> languages = programmingLanguageService.getAllProgrammingLanguages();
        List<ProgrammingLanguageDto> languagesDto = List.copyOf(languageConverter.convertAll(languages));
        return new OptionsForSingleCheckSettingsDto(languagesDto);
    }

    @Override
    public SingleCheckPlagDetectionResultDto processSingleCheck(SingleCheckPlagDetectionDto dto) {
        ProgrammingLanguage programmingLanguage = programmingLanguageService
                .getProgrammingLanguageById(dto.getProgrammingLanguageId())
                .orElseThrow();
        PlagDetectionSettings setting = settingsConverter.convert(dto);
        setting.setProgrammingLanguage(programmingLanguage);

        generatePaths(setting, dto);

        PlagDetectionResult result = null;
        if (!fileSystemWriter.unzipFile(dto.getCodeToPlagDetectionZip(), setting.getDataPath())) {
            result = PlagDetectionResult.failed("Unable to unpack archive with code to plagiarism detection!");
        }
        if (Objects.isNull(result) &&
                Objects.nonNull(dto.getBaseCodeZip()) &&
                !fileSystemWriter.unzipFile(dto.getBaseCodeZip(), setting.getBaseCodePath())) {
            result = PlagDetectionResult.failed("Unable to unpack archive with base code to plagiarism detection!");
        }
        if (Objects.isNull(result)) {
            result = plagiarismDetectionService.processForSingleTask(setting);
        }
        plagDetectionResultService.savePlagDetectionResult(result);

        return resultConverter.convert(result);
    }

    private void generatePaths(PlagDetectionSettings setting, SingleCheckPlagDetectionDto dto) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String dateString = formatter.format(new Date());
        String fileName = dto.getCodeToPlagDetectionZip().getOriginalFilename();

        setting.setDataPath(
                Path.of(Paths.ZIP_DATA_FOLDER, dateString, fileName).toString());
        setting.setResultPath(
                Path.of(Paths.ANALYSIS_RESULT_FOLDER, dateString, fileName).toString());

        if (Objects.nonNull(dto.getBaseCodeZip())) {
            setting.setBaseCodePath(
                    Path.of(Paths.ZIP_DATA_FOLDER, dateString, fileName, JPlag.BASE_CODE_DIRECTORY).toString());
        }
    }

}

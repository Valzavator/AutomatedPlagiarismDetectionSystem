package com.autoplag.facade.impl;

import com.autoplag.config.constant.JPlag;
import com.autoplag.config.constant.Paths;
import com.autoplag.facade.Facade;
import com.autoplag.facade.SingleCheckPlagiarismDetectionFacade;
import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.PlagDetectionResult;
import com.autoplag.persistence.domain.PlagDetectionSettings;
import com.autoplag.persistence.domain.ProgrammingLanguage;
import com.autoplag.presentation.exception.BadRequestException;
import com.autoplag.presentation.payload.request.SingleCheckPlagDetectionDto;
import com.autoplag.presentation.payload.response.OptionsForSingleCheckSettingsDto;
import com.autoplag.presentation.payload.response.ProgrammingLanguageDto;
import com.autoplag.presentation.payload.response.SingleCheckPlagDetectionResultDto;
import com.autoplag.service.PlagDetectionResultService;
import com.autoplag.service.PlagDetectionService;
import com.autoplag.service.ProgrammingLanguageService;
import com.autoplag.util.FileSystemWriter;
import lombok.AllArgsConstructor;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Facade
@AllArgsConstructor
public class SingleCheckPlagiarismDetectionFacadeImpl implements SingleCheckPlagiarismDetectionFacade {
    public static final String DATE_FORMAT = "yyyy-MM-dd" + File.separator + "HH-mm-ss";

    private final ProgrammingLanguageService programmingLanguageService;
    private final PlagDetectionService plagiarismDetectionService;
    private final PlagDetectionResultService plagDetectionResultService;

    private final FileSystemWriter fileSystemWriter;
    private final Converter<ProgrammingLanguage, ProgrammingLanguageDto> languageConverter;
    private final Converter<SingleCheckPlagDetectionDto, PlagDetectionSettings> settingsConverter;
    private final Converter<PlagDetectionResult, SingleCheckPlagDetectionResultDto> resultConverter;

    @Override
    public OptionsForSingleCheckSettingsDto getOptionsForSingleCheckSettings() {
        List<ProgrammingLanguage> languages = programmingLanguageService.getAllProgrammingLanguages();
        List<ProgrammingLanguageDto> languageDtos = languageConverter.convertAll(languages);
        return new OptionsForSingleCheckSettingsDto(languageDtos);
    }

    @Override
    public SingleCheckPlagDetectionResultDto processSingleCheck(SingleCheckPlagDetectionDto dto) {
        //TODO - change to ResourceNotFoundException
        ProgrammingLanguage programmingLanguage = programmingLanguageService
                .getProgrammingLanguageById(dto.getProgrammingLanguageId())
                .orElseThrow(BadRequestException::new);
        PlagDetectionSettings settings = settingsConverter.convert(dto);
        settings.setProgrammingLanguage(programmingLanguage);

        generatePaths(settings, dto);

        PlagDetectionResult result = null;
        if (!fileSystemWriter.unzipFile(dto.getCodeToPlagDetectionZip(), settings.getDataPath())) {
            result = PlagDetectionResult.failed("Unable to unpack archive with code to plagiarism detection!");
        }
        if (Objects.isNull(result) &&
                Objects.nonNull(dto.getBaseCodeZip()) &&
                !fileSystemWriter.unzipFile(dto.getBaseCodeZip(), settings.getBaseCodePath())) {
            result = PlagDetectionResult.failed("Unable to unpack archive with base code to plagiarism detection!");
        }
        if (Objects.isNull(result)) {
            result = plagiarismDetectionService.processForSingleTask(settings);
        }
        plagDetectionResultService.savePlagDetectionResult(result);

        return resultConverter.convert(result);
    }

    private void generatePaths(PlagDetectionSettings settings, SingleCheckPlagDetectionDto dto) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String dateString = formatter.format(new Date());
        String fileName = dto.getCodeToPlagDetectionZip().getOriginalFilename();

        settings.setDataPath(
                Path.of(Paths.ZIP_DATA_FOLDER, dateString, fileName).toString());
        settings.setResultPath(
                Path.of(Paths.ANALYSIS_RESULT_FOLDER, dateString, fileName).toString());

        if (Objects.nonNull(dto.getBaseCodeZip())) {
            settings.setBaseCodePath(
                    Path.of(Paths.ZIP_DATA_FOLDER, dateString, fileName, JPlag.BASE_CODE_DIRECTORY).toString());
        }
    }

}

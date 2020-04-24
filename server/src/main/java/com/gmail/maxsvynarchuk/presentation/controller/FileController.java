package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.SinglePlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.presentation.payload.request.PlagDetectionSettingDto;
import com.gmail.maxsvynarchuk.presentation.payload.request.ProgrammingLanguageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/test")
@AllArgsConstructor
@Slf4j
public class FileController {
    private static final String ZIP_CONTENT_TYPE = "application/zip";

    private final SinglePlagiarismDetectionFacade singlePlagiarismDetectionFacade;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "java19") String lang)
            throws IOException {
        if (!ZIP_CONTENT_TYPE.equals(file.getContentType())) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }

        log.info(String.format("File name '%s' uploaded successfully.", file.getOriginalFilename()));
        log.info(String.format("File name '%s' uploaded successfully.", file.getName()));
        log.info(String.format("File name '%s' uploaded successfully.", file.getSize()));
        log.info(String.format("File name '%s' uploaded successfully.", file.getContentType()));

        PlagDetectionSettingDto settingDto = new PlagDetectionSettingDto();
        ProgrammingLanguageDto languageDto = new ProgrammingLanguageDto(1, lang);
        settingDto.setProgrammingLanguage(languageDto);

        PlagDetectionResult result = singlePlagiarismDetectionFacade.processForZipFile(settingDto, file);

        return ResponseEntity.ok().body(result);
    }

}


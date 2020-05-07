package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.SinglePlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionDto;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionResultDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSingleCheckSettingsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/single-check")
@AllArgsConstructor
@Slf4j
public class SingleCheckPlagiarismController {
    private final SinglePlagiarismDetectionFacade singlePlagiarismDetectionFacade;

    private static final String ZIP_CONTENT_TYPE = "zip";

    @GetMapping("/options")
    public ResponseEntity<?> getOptionsForSingleCheckSettings() {
        OptionsForSingleCheckSettingsDto optionsForSettings =
                singlePlagiarismDetectionFacade.getOptionsForSingleCheckSettings();
        return ResponseEntity.ok().body(optionsForSettings);
    }

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> singleCheckPlagiarismDetection(
            @Valid SingleCheckPlagDetectionDto dto)
            throws HttpMediaTypeNotSupportedException {
        if (!validateZipMediaType(dto.getCodeToPlagDetectionZip())) {
            throw new HttpMediaTypeNotSupportedException("Invalid 'codeToPlagDetectionZip' media type!");
        }

        if (Objects.nonNull(dto.getBaseCodeZip()) &&
                dto.getBaseCodeZip().getSize() <= 0) {
            dto.setBaseCodeZip(null);
        } else if (Objects.nonNull(dto.getBaseCodeZip()) &&
                !validateZipMediaType(dto.getBaseCodeZip())) {
            throw new HttpMediaTypeNotSupportedException("Invalid 'baseCodeZip' media type!");
        }


        long startTime = System.nanoTime();
        SingleCheckPlagDetectionResultDto result = singlePlagiarismDetectionFacade.processSingleCheck(dto);
        long stopTime = System.nanoTime();
        System.out.println((stopTime - startTime) / 1000000000);

        return ResponseEntity.ok().body(result);
    }

    private boolean validateZipMediaType(MultipartFile file) {
        return Objects.nonNull(file) &&
                Objects.nonNull(file.getContentType()) &&
                file.getContentType().contains(ZIP_CONTENT_TYPE);
    }
}

package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.SingleCheckPlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.SingleCheckPlagDetectionResultDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSingleCheckSettingsDto;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/single-check")
@AllArgsConstructor
@Slf4j
public class SingleCheckPlagiarismController {
    private final SingleCheckPlagiarismDetectionFacade singleCheckPlagiarismDetectionFacade;

    @GetMapping("/options")
    public ResponseEntity<?> getOptionsForSingleCheckSettings() {
        OptionsForSingleCheckSettingsDto optionsForSettings =
                singleCheckPlagiarismDetectionFacade.getOptionsForSingleCheckSettings();
        return ResponseEntity.ok().body(optionsForSettings);
    }

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> singleCheckPlagiarismDetection(
            @Valid SingleCheckPlagDetectionDto dto)
            throws HttpMediaTypeNotSupportedException {
        if (!ControllerUtil.validateZipMediaType(dto.getCodeToPlagDetectionZip())) {
            throw new HttpMediaTypeNotSupportedException("Invalid 'codeToPlagDetectionZip' media type!");
        }

        if (Objects.nonNull(dto.getBaseCodeZip()) &&
                dto.getBaseCodeZip().getSize() <= 0) {
            dto.setBaseCodeZip(null);
        } else if (Objects.nonNull(dto.getBaseCodeZip()) &&
                !ControllerUtil.validateZipMediaType(dto.getBaseCodeZip())) {
            throw new HttpMediaTypeNotSupportedException("Invalid 'baseCodeZip' media type!");
        }
        SingleCheckPlagDetectionResultDto result = singleCheckPlagiarismDetectionFacade.processSingleCheck(dto);
        return ResponseEntity.ok().body(result);
    }

}

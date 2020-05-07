package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.PlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.facade.SinglePlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionSettingsDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSingleCheckSettingsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/single-check")
@AllArgsConstructor
@Slf4j
public class SingleCheckPlagiarismController {
    private final PlagiarismDetectionFacade plagiarismDetectionFacade;
    private final SinglePlagiarismDetectionFacade singlePlagiarismDetectionFacade;

    private static final String ZIP_CONTENT_TYPE = "application/zip";

    @GetMapping("/options")
    public ResponseEntity<?> getOptionsForSingleCheckSettings() {
        OptionsForSingleCheckSettingsDto optionsForSettings =
                plagiarismDetectionFacade.getOptionsForSingleCheckSettings();
        return ResponseEntity.ok().body(optionsForSettings);
    }

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> singleCheckPlagiarismDetection(
            @Valid SingleCheckPlagDetectionSettingsDto singleCheckPlagDetectionSettingsDto) {

//        if (!ZIP_CONTENT_TYPE.equals(file.getContentType())) {
//            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
//        }

        log.debug(singleCheckPlagDetectionSettingsDto.toString());
        log.debug(singleCheckPlagDetectionSettingsDto.getCodeToPlagDetectionZip().getOriginalFilename());
        log.debug("{}",singleCheckPlagDetectionSettingsDto.getCodeToPlagDetectionZip().getSize());

        return ResponseEntity.ok().build();
    }
}

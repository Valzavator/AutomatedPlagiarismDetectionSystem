package com.gmail.maxsvynarchuk.presentation.util;

import com.gmail.maxsvynarchuk.presentation.payload.response.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

public class ControllerUtil {
    private static final String ZIP_CONTENT_TYPE = "zip";

    /**
     * Add next page to redirect
     *
     * @param pageToRedirect page to redirect
     */
    public static String redirectTo(String pageToRedirect) {
        return "redirect:" + pageToRedirect;
    }

    public static ResponseEntity<?> prepareResponse(Optional<?> entityOpt, String requestURI) {
        if (entityOpt.isPresent()) {
            return ResponseEntity.ok()
                    .body(entityOpt.get());
        }
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, requestURI);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    public static boolean validateZipMediaType(MultipartFile file) {
        return Objects.nonNull(file) &&
                Objects.nonNull(file.getContentType()) &&
                file.getContentType().contains(ZIP_CONTENT_TYPE);
    }

    public static URI getLocation(String endpointPath) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath().path(endpointPath)
                .buildAndExpand()
                .toUri();
    }

    public static URI getLocation(String... pathSegments) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath().pathSegment(pathSegments)
                .buildAndExpand()
                .toUri();
    }
}

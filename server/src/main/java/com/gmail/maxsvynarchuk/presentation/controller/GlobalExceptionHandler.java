package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.presentation.payload.response.error.ApiError;
import com.gmail.maxsvynarchuk.presentation.payload.response.error.ApiValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<Object> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex,
                                                                 HttpServletRequest request) {
        log.debug(ex.toString());
        return buildResponseEntity(
                new ApiError(HttpStatus.PAYLOAD_TOO_LARGE, request.getRequestURI(), ex));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.debug(ex.toString());

        List<ApiValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    return ApiValidationError.builder()
                            .object(fieldError.getObjectName())
                            .field(fieldError.getField())
                            .rejectedValue(fieldError.getRejectedValue())
                            .message(fieldError.getDefaultMessage())
                            .build();
                })
                .collect(Collectors.toList());
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ApiError apiError = new ApiError(status, path, ex);
        apiError.setSubErrors(errors);

        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }
}
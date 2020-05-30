package com.autoplag.presentation.controller;

import com.autoplag.presentation.payload.response.error.ApiError;
import com.autoplag.presentation.payload.response.error.ApiValidationError;
import com.autoplag.service.exception.BadRequestException;
import com.autoplag.service.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class,
            EmptyResultDataAccessException.class})
    protected ResponseEntity<Object> handleResourceNotFound(RuntimeException ex,
                                                            HttpServletRequest request) {
        log.debug("", ex);
        return buildResponseEntity(
                new ApiError(HttpStatus.NOT_FOUND, request.getRequestURI(), ex));
    }

    @ExceptionHandler({BadRequestException.class,
            IllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequest(RuntimeException ex,
                                                   HttpServletRequest request) {
        log.debug(ex.toString());
        return buildResponseEntity(
                new ApiError(HttpStatus.BAD_REQUEST, request.getRequestURI(), ex));
    }

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
                .map(fieldError -> ApiValidationError.builder()
                        .object(fieldError.getObjectName())
                        .field(fieldError.getField())
                        .rejectedValue(fieldError.getRejectedValue())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ApiError apiError = new ApiError(status, path, ex);
        apiError.setSubErrors(errors);

        return buildResponseEntity(apiError);
    }

    // This handler hides the stack trace for API-clients in exception message
    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleAllException(
            Throwable ex,
            HttpServletRequest request) throws Throwable {
        if (ex instanceof BadCredentialsException) {
            throw ex;
        }

        log.error("", ex);
        return buildResponseEntity(
                new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI(), ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return delegateTemplateResponse(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {
        return delegateTemplateResponse(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        return delegateTemplateResponse(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {
        return delegateTemplateResponse(ex, headers, status, request);
    }

    private ResponseEntity<Object> delegateTemplateResponse(Exception ex,
                                                            HttpHeaders headers,
                                                            HttpStatus status,
                                                            WebRequest request) {
        log.debug(ex.toString());
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return buildResponseEntity(
                new ApiError(status, path, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }
}
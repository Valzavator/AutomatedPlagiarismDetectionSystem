package com.gmail.maxsvynarchuk.presentation.payload.response.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ApiError {
    private Date timestamp = new Date();;
    private Integer status;
    private String error;
    private String message;
    private String debugMessage;
    private String path;
    private List<? extends ApiSubError> subErrors = new ArrayList<>();

    public ApiError(HttpStatus status) {
        this.status = status.value();
        this.error = status.name();
    }

    public ApiError(HttpStatus status, String path) {
        this(status);
        this.path = path;
    }

    public ApiError(HttpStatus status, String path, Throwable ex) {
        this(status, path);
        this.message = status.getReasonPhrase();
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String path, String message, Throwable ex) {
        this(status, path, ex);
        this.message = message;
    }
}

package com.autoplag.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FailedToWriteToFileSystemException extends RuntimeException {

    private static final long serialVersionUID = -5077613551027077708L;

    public FailedToWriteToFileSystemException() {
    }

    public FailedToWriteToFileSystemException(String message) {
        super(message);
    }

    public FailedToWriteToFileSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToWriteToFileSystemException(Throwable cause) {
        super(cause);
    }
}
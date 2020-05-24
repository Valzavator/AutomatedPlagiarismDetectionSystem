package com.autoplag.service.exception;

/**
 * Artificial exception that should be thrown out of the Service layer
 */
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
package com.gmail.maxsvynarchuk.presentation.exception;

/**
 * Artificial exception that should be thrown out of the Presentation layer
 */
public class PresentationException extends RuntimeException {
    private static final long serialVersionUID = -707091962073683935L;

    public PresentationException() {
    }

    public PresentationException(String message) {
        super(message);
    }

    public PresentationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PresentationException(Throwable cause) {
        super(cause);
    }
}

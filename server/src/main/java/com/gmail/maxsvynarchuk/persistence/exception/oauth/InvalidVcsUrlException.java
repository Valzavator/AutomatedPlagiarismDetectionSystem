package com.gmail.maxsvynarchuk.persistence.exception.oauth;

/**
 * Artificial exception that should be thrown out of the Service layer
 */
public class InvalidVcsUrlException extends VCSException {

    private static final long serialVersionUID = -3667363643553607200L;

    public InvalidVcsUrlException() {
    }

    public InvalidVcsUrlException(String message) {
        super(message);
    }

    public InvalidVcsUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVcsUrlException(Throwable cause) {
        super(cause);
    }
}
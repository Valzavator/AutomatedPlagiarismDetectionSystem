package com.gmail.maxsvynarchuk.persistence.exception.oauth;

import com.gmail.maxsvynarchuk.persistence.exception.PersistenceException;

/**
 * Artificial exception that should be thrown out of the Service layer
 */
public class InvalidVcsUrlException extends PersistenceException {

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
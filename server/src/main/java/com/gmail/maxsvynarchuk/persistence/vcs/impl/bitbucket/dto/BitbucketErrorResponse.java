package com.gmail.maxsvynarchuk.persistence.vcs.impl.bitbucket.dto;

import lombok.*;

@Data
public class BitbucketErrorResponse {
    private int status;

    private String statusText;

    private String type;

    @Setter(AccessLevel.NONE)
    private Error error;

    public void setError(String error) {
        this.error = new Error(error);
    }

    @Getter
    @ToString
    @AllArgsConstructor
    private static class Error {
        private String message;
    }

}

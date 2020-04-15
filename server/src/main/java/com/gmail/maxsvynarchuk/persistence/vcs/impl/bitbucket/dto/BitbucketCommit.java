package com.gmail.maxsvynarchuk.persistence.vcs.impl.bitbucket.dto;

import lombok.*;

import java.util.Date;

@Getter
@ToString
public class BitbucketCommit {
    private String hash;

    private Date date;
}

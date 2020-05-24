package com.autoplag.persistence.vcs.impl.bitbucket.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class BitbucketCommit {
    private String hash;

    private Date date;
}

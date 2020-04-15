package com.gmail.maxsvynarchuk.persistence.vcs.impl.bitbucket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class BitbucketPagination<T> {
    List<T> values;

    String next;
}

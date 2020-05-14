package com.gmail.maxsvynarchuk.presentation.payload.response;

import com.gmail.maxsvynarchuk.persistence.domain.*;
import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BasicTaskGroupDto {
    private TaskGroupKey id;

    private Long taskId;
    private String taskName;

    private Date creationDate;
    private Date expiryDate;
    private PlagDetectionStatus plagDetectionStatus;
}

package com.autoplag.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public
class TaskGroupKey implements Serializable {
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "group_id")
    private Long groupId;
}

package com.gmail.maxsvynarchuk.persistence.domain;

import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "task_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "TaskGroup.detail",
        attributeNodes = {
                @NamedAttributeNode(value = "group", subgraph = "TaskGroup.Group.detail"),
                @NamedAttributeNode("task"),
                @NamedAttributeNode(value = "plagDetectionSettings", subgraph = "TaskGroup.PlagDetectionStatus.detail")
        },
        subgraphs = {
                @NamedSubgraph(name = "TaskGroup.Group.detail",
                        attributeNodes = {
                                @NamedAttributeNode(value = "studentGroups", subgraph = "TaskGroup.Group.StudentGroup.detail"),
                                @NamedAttributeNode(value = "course", subgraph = "TaskGroup.Group.Course.detail")
                        }
                ),
                @NamedSubgraph(name = "TaskGroup.PlagDetectionStatus.detail",
                        attributeNodes = {
                                @NamedAttributeNode("programmingLanguage")
                        }
                ),
                @NamedSubgraph(name = "TaskGroup.Group.StudentGroup.detail",
                        attributeNodes = {
                                @NamedAttributeNode(value = "student")
                        }
                ),
                @NamedSubgraph(name = "TaskGroup.Group.Course.detail",
                        attributeNodes = {
                                @NamedAttributeNode(value = "creator", subgraph = "TaskGroup.Group.Course.User.detail")
                        }
                ),
                @NamedSubgraph(name = "TaskGroup.Group.Course.User.detail",
                        attributeNodes = {
                                @NamedAttributeNode("tokens")
                        }
                )
        }
)
public class TaskGroup implements Serializable {
    @EmbeddedId
    private TaskGroupKey id;

    @MapsId("group_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @MapsId("task_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @NotNull
    private Date creationDate;

    @NotNull
    private Date expiryDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PlagDetectionStatus plagDetectionStatus;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "plagiarism_detection_setting_id", nullable = false)
    private PlagDetectionSettings plagDetectionSettings;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "plagiarism_detection_result_id")
    private PlagDetectionResult plagDetectionResult;
}
package com.gmail.maxsvynarchuk.persistence.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "Group.detail",
        attributeNodes = {
                @NamedAttributeNode(value = "course"),
                @NamedAttributeNode(value = "studentGroups", subgraph = "Group.StudentGroup.detail"),
                @NamedAttributeNode(value = "taskGroups", subgraph = "Group.TaskGroup.detail")
        },
        subgraphs = {
                @NamedSubgraph(name = "Group.StudentGroup.detail",
                        attributeNodes = {
                                @NamedAttributeNode(value = "student")
                        }
                ),
                @NamedSubgraph(name = "Group.TaskGroup.detail",
                        attributeNodes = {
                                @NamedAttributeNode("task")
                        }
                )
        }
)
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<StudentGroup> studentGroups;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TaskGroup> taskGroups;
}

package com.autoplag.persistence.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @Lob
    private String description;

    @NotNull
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "course")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Group> groups;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "course")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<StudentGroup> studentGroups;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "course")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Task> tasks;
}

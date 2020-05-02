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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JoinTable(name = "student_group",
//            joinColumns = {@JoinColumn(name = "group_id")},
//            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    @ToString.Exclude
    private Set<StudentGroup> studentGroups;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    @ToString.Exclude
    private Set<TaskGroup> taskGroups;
}

package com.gmail.maxsvynarchuk.persistence.domain;

import com.gmail.maxsvynarchuk.persistence.domain.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @NaturalId
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole name;
}

package com.gmail.maxsvynarchuk.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "programming_languages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammingLanguage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "programming_language_id")
    private Integer id;

    @NotBlank
    @Size(max = 255)
    private String name;
}

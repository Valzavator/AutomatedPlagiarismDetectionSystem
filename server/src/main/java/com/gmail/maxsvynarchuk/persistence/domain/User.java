package com.gmail.maxsvynarchuk.persistence.domain;

import com.gmail.maxsvynarchuk.persistence.domain.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable  {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Gender gender;

    private Date dateOfBirth;

    private List<Course> courses;
}

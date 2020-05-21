package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Optional<Student> getStudentById(Long studentId);

    Page<Student> getAllStudentsByCreatorId(Long creatorId, int page, int size);

    List<Student> getAllStudentsNotAddedToCourse(Long userId, Long courseId);

}

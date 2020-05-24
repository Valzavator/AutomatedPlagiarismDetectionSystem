package com.autoplag.service;

import com.autoplag.persistence.domain.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Optional<Student> getStudentById(Long studentId);

    Page<Student> getAllStudentsByCreatorId(Long creatorId, int page, int size);

    List<Student> getAllStudentsNotAddedToCourse(Long userId, Long courseId);

    boolean saveStudent(Student student);

    boolean deleteStudentFromSystem(Long studentId);

}

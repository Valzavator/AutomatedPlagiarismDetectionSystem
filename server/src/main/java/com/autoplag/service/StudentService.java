package com.autoplag.service;

import com.autoplag.persistence.domain.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    Page<Student> getAllStudentsByCreatorId(Long creatorId, int page, int size);

    List<Student> getAllStudentsNotAddedToCourse(Long creatorId, Long courseId);

    boolean saveStudent(Long creatorId, Student student);

    void deleteStudentFromSystem(Long studentId);

}

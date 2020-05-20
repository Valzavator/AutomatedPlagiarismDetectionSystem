package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    Page<Student> getAllStudentsByCreatorId(Long creatorId, int page, int size);

    List<Student> getAllStudentsNotAddedToCourse(Long userId, Long courseId);

}

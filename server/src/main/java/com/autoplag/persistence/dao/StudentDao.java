package com.autoplag.persistence.dao;

import com.autoplag.persistence.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface StudentDao extends GenericDao<Student, Long> {

    Page<Student> findByCreatorId(Long creatorId, Pageable pageable);

    List<Student> findAllNotAddedToCourse(Long userId, Long courseId, Sort sort);

    boolean existByFullName(String fullName);

}

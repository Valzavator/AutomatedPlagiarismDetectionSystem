package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentDao extends GenericDao<Student, Long> {

    Page<Student> findByCreatorId(Long creatorId, Pageable pageable);

}

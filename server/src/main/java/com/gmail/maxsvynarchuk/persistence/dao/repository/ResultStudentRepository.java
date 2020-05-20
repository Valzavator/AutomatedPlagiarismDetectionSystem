package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.ResultStudent;
import com.gmail.maxsvynarchuk.persistence.domain.ResultStudentKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultStudentRepository extends JpaRepository<ResultStudent, ResultStudentKey> {
}
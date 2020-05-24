package com.autoplag.persistence.dao.repository;

import com.autoplag.persistence.domain.ResultStudent;
import com.autoplag.persistence.domain.ResultStudentKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultStudentRepository extends JpaRepository<ResultStudent, ResultStudentKey> {
}
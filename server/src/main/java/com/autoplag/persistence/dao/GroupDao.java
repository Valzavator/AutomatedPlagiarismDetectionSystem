package com.autoplag.persistence.dao;

import com.autoplag.persistence.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupDao extends GenericDao<Group, Long> {

    Page<Group> findByCourseId(Long courseId, Pageable pageable);

    void deleteByIdAndCourseId(Long groupId, Long courseId);

}

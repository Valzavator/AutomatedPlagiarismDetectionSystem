package com.autoplag.persistence.dao.repository;

import com.autoplag.persistence.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Page<Group> findByCourseId(Long courseId, Pageable pageable);

    @EntityGraph(value = "Group.detail", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Group> findById(Long groupId);

    long deleteByIdAndCourseId(Long groupId, Long courseId);

}
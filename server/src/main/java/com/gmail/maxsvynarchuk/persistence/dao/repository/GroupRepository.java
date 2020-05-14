package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.NamedEntityGraph;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Page<Group> findByCourseId(Long courseId, Pageable pageable);

    @EntityGraph(value = "Group.detail", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Group> findById(Long groupId);

}
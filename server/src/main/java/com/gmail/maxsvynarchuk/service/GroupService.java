package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.Group;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface GroupService {

    Page<Group> getAllGroupsByCourseId(Long courseId,
                                       int page,
                                       int size);

    Optional<Group> getGroupById(Long groupId);

}

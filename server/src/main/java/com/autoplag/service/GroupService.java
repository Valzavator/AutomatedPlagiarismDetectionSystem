package com.autoplag.service;

import com.autoplag.persistence.domain.Group;
import org.springframework.data.domain.Page;

public interface GroupService {

    Page<Group> getAllGroupsByCourseId(Long courseId, int page, int size);

    Group getGroupById(Long groupId);

    Group saveGroup(Long creatorId, Long courseId, Group group);

    void deleteGroupFromCourse(Long groupId, Long courseId);

}

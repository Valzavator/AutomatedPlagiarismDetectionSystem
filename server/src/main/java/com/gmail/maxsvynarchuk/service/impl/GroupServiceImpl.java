package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.GroupDao;
import com.gmail.maxsvynarchuk.persistence.domain.Group;
import com.gmail.maxsvynarchuk.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupDao groupDao;

    @Override
    public Page<Group> getGroupsByCourseId(Long courseId,
                                           int page,
                                           int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.desc("creationDate")));
        return groupDao.findByCourseId(courseId, pageable);
    }

    @Override
    public Optional<Group> getGroupById(Long groupId) {
        return groupDao.findOne(groupId);
    }

}

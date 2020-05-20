package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.StudentDao;
import com.gmail.maxsvynarchuk.persistence.domain.Student;
import com.gmail.maxsvynarchuk.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    @Transactional(readOnly = true)
    @Override
    public Page<Student> getAllStudentsByCreatorId(Long creatorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.desc("id")));
        return studentDao.findByCreatorId(creatorId, pageable);
    }

}

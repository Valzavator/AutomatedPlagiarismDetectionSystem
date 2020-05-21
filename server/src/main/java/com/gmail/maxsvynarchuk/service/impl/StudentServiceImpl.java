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

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<Student> getStudentById(Long studentId) {
        //TODO - find by id and creatorId
        return studentDao.findOne(studentId);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Student> getAllStudentsByCreatorId(Long creatorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.asc("fullName")));
        return studentDao.findByCreatorId(creatorId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Student> getAllStudentsNotAddedToCourse(Long userId, Long courseId) {
        Sort sort = Sort.by(Sort.Order.asc("fullName"));
        return studentDao.findAllNotAddedToCourse(userId, courseId, sort);
    }

}

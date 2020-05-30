package com.autoplag.service.impl;

import com.autoplag.persistence.dao.StudentDao;
import com.autoplag.persistence.domain.Student;
import com.autoplag.persistence.domain.User;
import com.autoplag.service.StudentService;
import com.autoplag.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final UserService userService;
    private final StudentDao studentDao;

    @Transactional(readOnly = true)
    @Override
    public Page<Student> getAllStudentsByCreatorId(Long creatorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.asc("fullName")));
        return studentDao.findByCreatorId(creatorId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Student> getAllStudentsNotAddedToCourse(Long creatorId, Long courseId) {
        Sort sort = Sort.by(Sort.Order.asc("fullName"));
        return studentDao.findAllNotAddedToCourse(creatorId, courseId, sort);
    }

    @Override
    public boolean saveStudent(Long creatorId, Student student) {
        String fullName = student.getFullName().trim()
                .replaceAll("\\s+", " ");
        student.setFullName(fullName);
        if (studentDao.existByFullName(student.getFullName())) {
            return false;
        }
        User user = userService.getUserById(creatorId);
        student.setCreator(user);
        studentDao.save(student);
        return true;
    }

    @Override
    public void deleteStudentFromSystem(Long studentId) {
        studentDao.deleteById(studentId);
    }

}

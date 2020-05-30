package com.autoplag.service.impl;

import com.autoplag.persistence.dao.ProgrammingLanguageDao;
import com.autoplag.persistence.domain.ProgrammingLanguage;
import com.autoplag.service.ProgrammingLanguageService;
import com.autoplag.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ProgrammingLanguageServiceImpl implements ProgrammingLanguageService {
    private final ProgrammingLanguageDao programmingLanguageDao;

    @Override
    public ProgrammingLanguage getProgrammingLanguageById(Integer programmingLanguageId) {
        return programmingLanguageDao.findOne(programmingLanguageId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<ProgrammingLanguage> getAllProgrammingLanguages() {
        return programmingLanguageDao.findAll();
    }
}

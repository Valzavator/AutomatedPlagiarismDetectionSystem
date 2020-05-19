package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.PlagDetectionResultDao;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.persistence.domain.ResultStudent;
import com.gmail.maxsvynarchuk.persistence.domain.StudentGroup;
import com.gmail.maxsvynarchuk.service.PlagDetectionResultService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class PlagDetectionResultServiceImpl implements PlagDetectionResultService {
    private final PlagDetectionResultDao plagDetectionResultDao;

    @Transactional
    @Override
    public PlagDetectionResult savePlagDetectionResult(PlagDetectionResult result) {
        return plagDetectionResultDao.save(result);
    }
}

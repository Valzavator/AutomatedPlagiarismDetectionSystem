package com.autoplag.service.impl;

import com.autoplag.persistence.dao.PlagDetectionResultDao;
import com.autoplag.persistence.domain.PlagDetectionResult;
import com.autoplag.service.PlagDetectionResultService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

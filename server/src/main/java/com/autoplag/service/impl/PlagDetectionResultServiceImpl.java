package com.autoplag.service.impl;

import com.autoplag.persistence.dao.PlagDetectionResultDao;
import com.autoplag.persistence.domain.PlagDetectionResult;
import com.autoplag.service.PlagDetectionResultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PlagDetectionResultServiceImpl implements PlagDetectionResultService {
    private final PlagDetectionResultDao plagDetectionResultDao;

    @Override
    public PlagDetectionResult savePlagDetectionResult(PlagDetectionResult result) {
        return plagDetectionResultDao.save(result);
    }
}

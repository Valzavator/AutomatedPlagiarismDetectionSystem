package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.StudentGroupDao;
import com.gmail.maxsvynarchuk.persistence.domain.*;
import com.gmail.maxsvynarchuk.persistence.domain.type.DetectionType;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.RepositoryInfo;
import com.gmail.maxsvynarchuk.persistence.plagiarism.SoftwarePlagDetectionTool;
import com.gmail.maxsvynarchuk.service.PlagiarismDetectionService;
import com.gmail.maxsvynarchuk.service.vcs.VcsDownloadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class DetectionServiceImpl implements PlagiarismDetectionService {
    private final VcsDownloadService vcsDownloadService;
    private final SoftwarePlagDetectionTool softwarePlagDetectionTool;
    private final StudentGroupDao studentGroupDao;

    @Transactional
    @Override
    public PlagDetectionResult processForTaskGroup(TaskGroup taskGroup) {
        log.debug("Attempt to process taskGroup ({})", taskGroup.getId());

        User user = taskGroup.getGroup().getCourse().getCreator();
        Task task = taskGroup.getTask();
        PlagDetectionSettings setting = taskGroup.getPlagDetectionSettings();
        Set<StudentGroup> studentGroups = loadStudents(taskGroup);

        for (StudentGroup studentGroup : studentGroups) {
            String studentFullName = studentGroup.getStudent().getFullName();
            String repositoryDataPath = setting.getDataPath() + studentFullName + "/";
            String vcsRepositoryUrl = studentGroup.getVcsRepositoryUrl();
            AccessToken accessToken = user.getAccessToken(vcsRepositoryUrl);
            if (Objects.isNull(accessToken)) {
                // TODO save some message to PlagDetectionResult
                continue;
            }
            Optional<RepositoryInfo> repositoryInfo = vcsDownloadService.downloadRepository(
                    accessToken,
                    vcsRepositoryUrl,
                    task.getRepositoryPrefixPath(),
                    taskGroup.getExpiryDate());
            if (repositoryInfo.isPresent()) {
                vcsDownloadService.downloadAndSaveRawContentOfFiles(accessToken,
                        repositoryInfo.get(),
                        repositoryDataPath);
            } else {
                // TODO add some message about failed attempt to download repository
            }
        }

        return softwarePlagDetectionTool.generateHtmlResult(setting);
    }

    @Override
    public PlagDetectionResult processForSingleTask(PlagDetectionSettings setting) {
        log.debug("Attempt to process single task ({})", setting.getDataPath());
        return softwarePlagDetectionTool.generateHtmlResult(setting);
    }

    private Set<StudentGroup> loadStudents(TaskGroup taskGroup) {
        DetectionType detectionType = taskGroup.getPlagDetectionSettings().getDetectionType();
        if (DetectionType.GROUP == detectionType) {
            log.debug("Get students from group ({})", taskGroup.getGroup().getId());
            return taskGroup.getGroup().getStudentGroups();
        } else if (DetectionType.COURSE == detectionType) {
            log.debug("Get students from course ({})", taskGroup.getGroup().getCourse().getId());
            return studentGroupDao.findAllWhoHaveTask(taskGroup.getTask());
        }
        throw new IllegalArgumentException(detectionType.toString());
    }

}

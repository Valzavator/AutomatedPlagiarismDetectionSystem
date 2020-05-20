package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.StudentGroupDao;
import com.gmail.maxsvynarchuk.persistence.domain.*;
import com.gmail.maxsvynarchuk.persistence.domain.type.DetectionType;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.RepositoryInfo;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.InvalidVcsUrlException;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.OAuthIllegalTokenException;
import com.gmail.maxsvynarchuk.persistence.plagiarism.SoftwarePlagDetectionTool;
import com.gmail.maxsvynarchuk.service.PlagDetectionResultService;
import com.gmail.maxsvynarchuk.service.PlagDetectionService;
import com.gmail.maxsvynarchuk.service.exception.FailedToWriteToFileSystemException;
import com.gmail.maxsvynarchuk.service.vcs.VcsDownloadService;
import com.gmail.maxsvynarchuk.util.FileSystemWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class PlagDetectionServiceImpl implements PlagDetectionService {
    private final VcsDownloadService vcsDownloadService;
    private final SoftwarePlagDetectionTool softwarePlagDetectionTool;
    private final StudentGroupDao studentGroupDao;
    private final PlagDetectionResultService plagDetectionResultService;
    private final FileSystemWriter fileSystemWriter;

    @Transactional
    @Override
    public PlagDetectionResult processForTaskGroup(TaskGroup taskGroup) {
        log.debug("Attempt to process taskGroup ({})", taskGroup.getId());

        User user = taskGroup.getGroup().getCourse().getCreator();
        Task task = taskGroup.getTask();
        PlagDetectionSettings setting = taskGroup.getPlagDetectionSettings();
        Set<StudentGroup> studentGroups = loadStudents(taskGroup);

        boolean wasReloaded = Objects.nonNull(setting.getBaseCodePath())
                ? fileSystemWriter.reloadDirectory(setting.getDataPath(), setting.getBaseCodePath())
                : fileSystemWriter.reloadDirectory(setting.getDataPath());

        if (!wasReloaded) {
            return PlagDetectionResult.failed("Server error!");
        }

        Set<ResultStudent> resultStudents = new HashSet<>();
        for (StudentGroup studentGroup : studentGroups) {
            String errorLogMessage = null;
            String studentFullName = studentGroup.getStudent().getFullName();
            String repositoryDataPath = Path.of(setting.getDataPath(), studentFullName).toString();
            String vcsRepositoryUrl = studentGroup.getVcsRepositoryUrl();
            AccessToken accessToken = user.getAccessToken(vcsRepositoryUrl);
            try {
                RepositoryInfo repositoryInfo = vcsDownloadService.downloadRepository(
                        accessToken,
                        vcsRepositoryUrl,
                        task.getRepositoryPrefixPath(),
                        taskGroup.getExpiryDate());
                if (repositoryInfo.isEmptyRepository()) {
                    errorLogMessage = "Directory \"" + task.getRepositoryPrefixPath() + "\" doesn`t exist!";
                } else {
                    vcsDownloadService.downloadAndSaveRawContentOfFiles(
                            accessToken,
                            repositoryInfo,
                            repositoryDataPath);
                }
            } catch (InvalidVcsUrlException ex) {
                log.error(ex.toString());
                errorLogMessage = "Invalid VCS-URL: " + studentGroup.getVcsRepositoryUrl();
            } catch (OAuthIllegalTokenException ex) {
                log.error(ex.toString());
                errorLogMessage = "No access to student repository: " + studentGroup.getVcsRepositoryUrl();
            } catch (FailedToWriteToFileSystemException ex) {
                log.error(ex.toString());
                errorLogMessage = "Failed to save student repository: " + studentGroup.getVcsRepositoryUrl();
            }
            if (Objects.nonNull(errorLogMessage)) {
                resultStudents.add(ResultStudent.builder()
                        .student(studentGroup.getStudent())
                        .logMessage(errorLogMessage)
                        .build());
            }
        }

        PlagDetectionResult result = softwarePlagDetectionTool.generateHtmlResult(setting);
        result = plagDetectionResultService.savePlagDetectionResult(result);
        if (!resultStudents.isEmpty()) {
            result.addResultStudents(resultStudents);
        }

        return plagDetectionResultService.savePlagDetectionResult(result);
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

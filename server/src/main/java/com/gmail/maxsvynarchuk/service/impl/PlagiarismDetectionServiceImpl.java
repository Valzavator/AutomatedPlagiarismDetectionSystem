package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.StudentDao;
import com.gmail.maxsvynarchuk.persistence.domain.*;
import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;
import com.gmail.maxsvynarchuk.persistence.domain.type.TypeDetection;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.plagiarism.SoftwarePlagDetectionTool;
import com.gmail.maxsvynarchuk.service.PlagiarismDetectionService;
import com.gmail.maxsvynarchuk.service.TaskGroupService;
import com.gmail.maxsvynarchuk.service.vcs.VcsDownloadToFileSystemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class PlagiarismDetectionServiceImpl implements PlagiarismDetectionService {
    private final VcsDownloadToFileSystemService vcsDownloadToFileSystemService;
    private final SoftwarePlagDetectionTool softwarePlagDetectionTool;
    private final StudentDao studentDao;

    @Transactional
    @Override
    public PlagDetectionResult process(TaskGroup taskGroup) {
        log.debug("Attempt to process taskGroup ({})", taskGroup.getId());

        User user = taskGroup.getGroup().getCourse().getCreator();
        Task task = taskGroup.getTask();
        PlagDetectionSetting setting = taskGroup.getPlagDetectionSetting();
        Set<Student> students = loadStudents(taskGroup);

        for (Student student : students) {
            String repositoryDataPath = setting.getDataPath() + student.getFullName() + "/";
            String vcsRepositoryUrl = student.getVcsRepositoryUrl();
            AccessToken accessToken = user.getAccessTokenForVcs(vcsRepositoryUrl);
            if (Objects.isNull(accessToken)) {
                // TODO save some message PlagDetectionResult
                continue;
            }
            vcsDownloadToFileSystemService.downloadOneRepository(
                    accessToken,
                    vcsRepositoryUrl,
                    task.getRepositoryPrefixPath(),
                    taskGroup.getExpiryDate(),
                    repositoryDataPath);

        }

        return softwarePlagDetectionTool.generateHtmlResult(setting);
    }

    private Set<Student> loadStudents(TaskGroup taskGroup) {
        TypeDetection typeDetection = taskGroup.getPlagDetectionSetting().getTypeDetection();
        if (TypeDetection.GROUP == typeDetection) {
            log.debug("Get students from group ({})", taskGroup.getGroup().getId());
            return taskGroup.getGroup().getStudents();
        } else if (TypeDetection.COURSE == typeDetection) {
            log.debug("Get students from course ({})", taskGroup.getGroup().getCourse().getId());
            return studentDao.findAllWhoHaveTask(taskGroup.getTask());
        }
        throw new IllegalArgumentException(typeDetection.toString());
    }

}

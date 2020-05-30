package com.autoplag.service.impl;

import com.autoplag.config.constant.JPlag;
import com.autoplag.config.constant.Paths;
import com.autoplag.persistence.dao.StudentGroupDao;
import com.autoplag.persistence.domain.*;
import com.autoplag.persistence.domain.type.DetectionType;
import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.persistence.domain.vcs.RepositoryInfo;
import com.autoplag.persistence.exception.oauth.InvalidVcsUrlException;
import com.autoplag.persistence.exception.oauth.OAuthIllegalTokenException;
import com.autoplag.persistence.plagiarism.SoftwarePlagDetectionTool;
import com.autoplag.service.PlagDetectionResultService;
import com.autoplag.service.PlagDetectionService;
import com.autoplag.service.exception.FailedToWriteToFileSystemException;
import com.autoplag.service.vcs.VcsDownloadService;
import com.autoplag.util.FileSystemWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class PlagDetectionServiceImpl implements PlagDetectionService {
    public static final String DATE_FORMAT = "yyyy-MM-dd" + File.separator + "HH-mm-ss";

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

    @Transactional
    @Override
    public PlagDetectionResult processForSingleTask(PlagDetectionSettings settings,
                                                    MultipartFile codeToPlagDetectionZip,
                                                    MultipartFile baseCodeZip) {
        log.debug("Attempt to process single task");

        generatePathsForSingleTask(settings,
                codeToPlagDetectionZip.getOriginalFilename(),
                Objects.nonNull(baseCodeZip));

        PlagDetectionResult result = null;
        if (!fileSystemWriter.unzipFile(codeToPlagDetectionZip, settings.getDataPath())) {
            result = PlagDetectionResult.failed("Unable to unpack archive with code to plagiarism detection!");
        }
        if (Objects.isNull(result) &&
                Objects.nonNull(baseCodeZip) &&
                !fileSystemWriter.unzipFile(baseCodeZip, settings.getBaseCodePath())) {
            result = PlagDetectionResult.failed("Unable to unpack archive with base code to plagiarism detection!");
        }
        if (Objects.isNull(result)) {
            result = softwarePlagDetectionTool.generateHtmlResult(settings);
        }
        return plagDetectionResultService.savePlagDetectionResult(result);
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

    private void generatePathsForSingleTask(PlagDetectionSettings settings,
                                            String codeToPlagDetectionZipFileName,
                                            boolean isBaseCodeZipExists) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String dateString = formatter.format(new Date());

        settings.setDataPath(
                Path.of(Paths.ZIP_DATA_FOLDER, dateString, codeToPlagDetectionZipFileName).toString());
        settings.setResultPath(
                Path.of(Paths.ANALYSIS_RESULT_FOLDER, dateString, codeToPlagDetectionZipFileName).toString());

        if (isBaseCodeZipExists) {
            settings.setBaseCodePath(
                    Path.of(Paths.ZIP_DATA_FOLDER, dateString, codeToPlagDetectionZipFileName, JPlag.BASE_CODE_DIRECTORY).toString());
        }
    }

}

package com.gmail.maxsvynarchuk.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class FileSystemWriter {
    private UnaryOperator<String> preProcessingStrategy;

    public FileSystemWriter() {

    }

    public FileSystemWriter(UnaryOperator<String> preProcessingStrategy) {
        this.preProcessingStrategy = preProcessingStrategy;
    }

    public void writeStringData(String filePathStr, String data) {
        if (Objects.isNull(filePathStr) || Objects.isNull(data)) {
            throw new IllegalArgumentException(filePathStr + " : " + data);
        }

        Path filePath = Paths.get(filePathStr);

        try {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);

            if (Objects.nonNull(preProcessingStrategy)) {
                data = preProcessingStrategy.apply(data);
            }
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write(data, 0, data.length());
            }
        } catch (FileAlreadyExistsException ex) {
            throw new IllegalStateException("File named " + filePath + " already exists!", ex);
        } catch (IOException ex) {
            throw new IllegalStateException("Create file " + filePath + " error!", ex);
        }
    }

    public String writeMultipartFile(String dataPath, MultipartFile file) {
        if (Objects.isNull(dataPath) || Objects.isNull(file)) {
            throw new IllegalArgumentException(dataPath + " : " + file);
        }

        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(dataPath + fileName);
        try {
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IllegalStateException("Create file " + filePath + " error!", ex);
        }

        return fileName;
    }

    public boolean deleteDirectory(String filePathStr) {
        Path filePath = Path.of(filePathStr);
        if (!Files.exists(filePath)) {
            return true;
        }
        try {
            Files.walk(filePath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            log.error("", e);
            return false;
        }
        return true;
    }

    public boolean unzipFile(MultipartFile file, String destDirPath) {
        File destDir = new File(destDirPath);
        byte[] buffer = new byte[1024];
        Charset charset = defineCharset();
        try (ZipInputStream zis = new ZipInputStream(file.getInputStream(), charset)) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                StringBuilder strBuffer = new StringBuilder();
                File newFile = newFile(destDir, zipEntry);
                if (!zipEntry.isDirectory()) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        strBuffer.append(new String(buffer, 0, len));
                    }
                    writeStringData(newFile.getPath(), strBuffer.toString());
                }
                zis.closeEntry();
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
        return true;
    }

    private File newFile(File destDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destDir, zipEntry.getName());

        String destDirPath = destDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destFile;
    }

    private Charset defineCharset() {
        if (Charset.isSupported("CP866")) {
            return Charset.forName("CP866");
        } else if (Charset.isSupported("ISO-8859-5")) {
            return Charset.forName("ISO-8859-5");
        }
        return Charset.defaultCharset();
    }

    public void setPreProcessingStrategy(UnaryOperator<String> preProcessingStrategy) {
        this.preProcessingStrategy = preProcessingStrategy;
    }
}

package com.gmail.maxsvynarchuk.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class FileSystemWriter {
    private UnaryOperator<String> preProcessingStrategy;

    public FileSystemWriter() {

    }

    public FileSystemWriter(UnaryOperator<String> preProcessingStrategy) {
        this.preProcessingStrategy = preProcessingStrategy;
    }

    public void write(String filePathStr, String data) {
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

    public boolean deleteDirectory(String filePathStr) {
        Path filePath = Path.of(filePathStr);
        try {
            Files.walk(filePath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void setPreProcessingStrategy(UnaryOperator<String> preProcessingStrategy) {
        this.preProcessingStrategy = preProcessingStrategy;
    }

}

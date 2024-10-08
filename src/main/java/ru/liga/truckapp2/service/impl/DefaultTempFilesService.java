package ru.liga.truckapp2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.service.TempFilesService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultTempFilesService implements TempFilesService {

    private final Random random;
    @Value("${temp-files.extension}")
    private String fileExtension;
    @Value("${temp-files.directory}")
    private String tempDir;

    @Override
    public String createTempFileFromContent(String content) {
        String fileName = tempDir + File.separator + random.nextLong() + "." + fileExtension;
        try {
            Path path = Path.of(fileName);
            Files.createDirectories(path.getParent());
            String filePath = Files.writeString(path, content).toString();
            log.debug("Created temp file: {}", filePath);
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Error occurred when creating tmp file '"
                    + fileName + ": " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String fileName) {
        Path path = Path.of(fileName);
        try {
            Files.delete(path);
            log.debug("Deleted temp file: {}", fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred when deleting tmp file '"
                    + path.getFileName() + ": " + e.getMessage());
        }

    }

}

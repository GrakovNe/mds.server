package org.grakovne.mds.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Component
public class FileProcessingUtils {
    private final static Logger logger = LoggerFactory.getLogger(FileProcessingUtils.class);

    @Autowired
    private ConfigurationUtils configurationProvider;

    public File getUploadFolder() {
        return new File(configurationProvider.getFileUploadDirectory());
    }

    public File uploadFile(MultipartFile file, String fileName) throws IOException {
        if (!Files.exists(getUploadFolder().toPath())) {
            createUploadDir();
        }

        String pathToSave = getUploadFolder() + File.separator + fileName;
        File fileToSave = new File(pathToSave);

        file.transferTo(fileToSave);
        return fileToSave;
    }

    public File getFile(String fileName) throws FileNotFoundException {
        String filePath = getUploadFolder() + File.separator + fileName;
        File file = new File(filePath);

        CheckerUtils.checkFileExists(file);

        return file;
    }

    public void deleteFile(String fileName) throws FileNotFoundException {
        getFile(fileName).delete();
    }

    private void createUploadDir() {
        File uploadDir = new File(configurationProvider.getFileUploadDirectory());
        uploadDir.mkdirs();
    }
}

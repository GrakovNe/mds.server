package org.grakovne.mds.server.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Works with story audio file in working directory.
 */

@SuppressWarnings("ResultOfMethodCallIgnored")
@Component
public class FileProcessingUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileProcessingUtils.class);

    @Autowired
    private ConfigurationUtils configurationProvider;

    /**
     * Returns working directory.
     *
     * @return file with working directory path
     */
    public File getUploadFolder() {
        return new File(configurationProvider.getFileUploadDirectory());
    }

    /**
     * Uploads file to working directory.
     *
     * @param file     content
     * @param fileName destination file name
     * @return saved file
     * @throws IOException when file can't be saved
     */

    public File uploadFile(MultipartFile file, String fileName) throws IOException {
        if (!Files.exists(getUploadFolder().toPath())) {
            createUploadDir();
        }

        String pathToSave = getUploadFolder() + File.separator + fileName;
        File fileToSave = new File(pathToSave);

        file.transferTo(fileToSave);
        return fileToSave;
    }

    /**
     * Uploads file to working directory.
     *
     * @param file     content
     * @param fileName destination file name
     * @return saved file
     * @throws IOException when file can't be saved
     */

    public File uploadFile(File file, String fileName) throws IOException {
        if (!Files.exists(getUploadFolder().toPath())) {
            createUploadDir();
        }

        String pathToSave = getUploadFolder() + File.separator + fileName;
        File fileToSave = new File(pathToSave);

        FileUtils.copyFile(file, fileToSave);
        return fileToSave;
    }


    /**
     * Returns file by it's name.
     *
     * @param fileName required file name
     * @return saved file
     * @throws FileNotFoundException when file isn't found on a disk
     */
    public File getFile(String fileName) throws FileNotFoundException {
        String filePath = getUploadFolder() + File.separator + fileName;
        File file = new File(filePath);

        CheckerUtils.checkFileExists(file);

        return file;
    }

    /**
     * Returns files in upload directory.
     *
     * @return list of files
     */
    public List<File> getFiles() {
        if (null == getUploadFolder().listFiles()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(Arrays.asList(getUploadFolder().listFiles()));
    }

    /**
     * Removes file by it's name.
     *
     * @param fileName required file name
     * @throws FileNotFoundException when file isn't found on a disk
     */
    public void deleteFile(String fileName) throws FileNotFoundException {
        getFile(fileName).delete();
    }

    /**
     * Creates working directory.
     */

    public void createUploadDir() {
        File uploadDir = new File(configurationProvider.getFileUploadDirectory());
        uploadDir.mkdirs();
    }

    /**
     * Deletes file if it exists.
     *
     * @param file file entity
     */

    public void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }
}

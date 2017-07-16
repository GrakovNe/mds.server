package org.grakovne.mds.server.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "spring.config")
public class ConfigurationUtils {

    private String fileUploadDirectory;

    public String getFileUploadDirectory() {
        return fileUploadDirectory;
    }

    public void setFileUploadDirectory(String fileUploadDirectory) {
        this.fileUploadDirectory = fileUploadDirectory;
    }
}

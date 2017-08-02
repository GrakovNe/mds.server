package org.grakovne.mds.server.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Works with spring configuration file.
 */

@Component
@ConfigurationProperties(prefix = "spring.config")
public class ConfigurationUtils {

    private String fileUploadDirectory;

    private Integer pageSize;

    public String getFileUploadDirectory() {
        return fileUploadDirectory;
    }

    public void setFileUploadDirectory(String fileUploadDirectory) {
        this.fileUploadDirectory = fileUploadDirectory;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

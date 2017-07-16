package org.grakovne.mds.server;

import org.grakovne.mds.server.configuration.CustomConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application's entry point.
 */
@SpringBootApplication
public class MdsApplication {

    /**
     * Main application class.
     *
     * @param args CLI args
     */
    public static void main(String[] args) {
        SpringApplication.run(MdsApplication.class, args);
    }
}


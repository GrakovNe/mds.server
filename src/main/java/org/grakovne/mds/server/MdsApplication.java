package org.grakovne.mds.server;

import org.grakovne.mds.server.services.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application's entry point.
 */
@SpringBootApplication
@EnableTransactionManagement
public class MdsApplication {

    @Autowired
    private SearchService searchService;

    private static final Logger logger = LoggerFactory.getLogger(MdsApplication.class);

    /**
     * Main application class.
     *
     * @param args CLI args
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MdsApplication.class, args);
    }
}


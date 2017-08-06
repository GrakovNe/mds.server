package org.grakovne.mds.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application's entry point.
 */
@SpringBootApplication
@EnableTransactionManagement
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


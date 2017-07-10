package org.grakovne.mds.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application's entry point.
 */
@SpringBootApplication
public class MdsPlayer {

    /**
     * Main application class.
     *
     * @param args CLI args
     */
    public static void main(String[] args) {
        SpringApplication.run(MdsPlayer.class, args);
    }
}


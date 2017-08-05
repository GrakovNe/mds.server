package org.grakovne.mds.server;

import org.grakovne.mds.server.repositories.StoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application's entry point.
 */
@SpringBootApplication
@EnableTransactionManagement
public class MdsApplication {

    @Autowired
    private StoryRepository storyRepository;

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


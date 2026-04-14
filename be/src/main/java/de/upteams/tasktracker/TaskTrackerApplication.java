package de.upteams.tasktracker;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main class of the App
 */
@SpringBootApplication
@EnableAsync
public class TaskTrackerApplication {

    /**
     * Method that starts the App and deploys it to nested Tomcat
     */
    public static void main(String[] args) {
        // Load .env file before Spring initialization
        Dotenv dotenv = Dotenv.configure()
                .filename(".env")
                .ignoreIfMissing()
                .load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(TaskTrackerApplication.class, args);
    }
}

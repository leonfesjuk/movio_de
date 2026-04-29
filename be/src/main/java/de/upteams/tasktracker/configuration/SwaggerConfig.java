package de.upteams.tasktracker.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI taskTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movio API")
                        .version("0.1.0")
                        .description("""
                                API with JWT in HTTP-only cookies.
                                
                                Includes development-only Test Data endpoints under /internal/test-data \
                                (available only for profile 'dev' and when testdata.api.enabled=true).""")
                        .contact(new Contact().name("Task Tracker Backend Team")));
    }
}

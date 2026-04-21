package de.upteams.tasktracker.validator;

import de.upteams.tasktracker.user.util.validation.url.ValidUrl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UrlValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    static class TestUrlDto {

        @ValidUrl
        private String webLink;

        TestUrlDto(String webLink) {
            this.webLink = webLink;
        }
    }

    @Test
    void shouldPassForValidHttpUrl() {
        TestUrlDto dto = new TestUrlDto("http://example.com");

        Set<ConstraintViolation<TestUrlDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldPassForValidHttpsUrl() {
        TestUrlDto dto = new TestUrlDto("https://example.com");

        Set<ConstraintViolation<TestUrlDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "abc",
            "example",
            "http://",
            "https://",
            "http://localhost",
            "ftp://example.com",
            "://example.com",
            "http:/example.com"
    })
    void shouldFailForInvalidUrls(String url) {
        TestUrlDto dto = new TestUrlDto(url);

        Set<ConstraintViolation<TestUrlDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassForNullValue() {
        TestUrlDto dto = new TestUrlDto(null);

        Set<ConstraintViolation<TestUrlDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }
}
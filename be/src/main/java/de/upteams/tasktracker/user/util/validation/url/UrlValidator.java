package de.upteams.tasktracker.user.util.validation.url;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.URI;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // @NotBlank проверит отдельно
        }

        String normalized = value.trim();

        // временно добавляем схему для парсинга
        if (!normalized.startsWith("http://") && !normalized.startsWith("https://")) {
            normalized = "https://" + normalized;
        }

        try {
            URI uri = new URI(normalized);

            String host = uri.getHost();

            return host != null && host.contains(".");

        } catch (Exception e) {
            return false;
        }
    }
}

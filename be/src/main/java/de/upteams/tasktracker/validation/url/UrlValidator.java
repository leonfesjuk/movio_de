package de.upteams.tasktracker.validation.url;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.URI;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // @NotBlank проверит отдельно
        }

        try {
            URI uri = new URI(value);

            String scheme = uri.getScheme();

            return scheme != null &&
                    (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) &&
                    uri.getHost() != null;

        } catch (Exception e) {
            return false;
        }
    }
}

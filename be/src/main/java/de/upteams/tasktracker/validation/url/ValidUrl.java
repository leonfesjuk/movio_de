package de.upteams.tasktracker.validation.url;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UrlValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUrl {

    String message() default "{user.webLink.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package br.com.raulalvesre.petshopadminservice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = { AdultAgeValidator.class })
public @interface AdultAge {
    String message() default "Date not greater than 18 years old";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

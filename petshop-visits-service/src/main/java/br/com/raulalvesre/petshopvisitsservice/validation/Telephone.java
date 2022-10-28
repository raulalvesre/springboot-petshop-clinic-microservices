package br.com.raulalvesre.petshopvisitsservice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Size(max = 15, message = "Telephone length must be less than or equal to 15")
@Pattern(regexp = "^\\((?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$",
        message = "Invalid telephone")
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = { })
public @interface Telephone {
    String message() default "Invalid telephone";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package com.springboot.springbootrestapi.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NameValidator.class)
public @interface NameValidation {

    String message() default "Enter a valid trail name from trails list"; // error message

    Class<?>[] groups() default { };                   // defines under which circumstances this validation is to be triggered

    Class<? extends Payload>[] payload() default { };

}

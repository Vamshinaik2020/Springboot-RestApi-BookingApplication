package com.springboot.springbootrestapi.validations;

import com.springboot.springbootrestapi.exception.TrailNotFoundException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class NameValidator implements ConstraintValidator<NameValidation, String> {
    List<String> trailsList = Arrays.asList("Kilimanjaro", "Everest Base Camp", "Annapurna Circuit", "Blue Falls", "Green Valley", "Brown Mountain");

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {  // here we use our logic
        if (!trailsList.contains(name)) {
            throw new TrailNotFoundException(context.getDefaultConstraintMessageTemplate());
        }
        return true;
    }
}

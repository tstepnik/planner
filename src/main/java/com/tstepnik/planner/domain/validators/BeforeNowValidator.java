package com.tstepnik.planner.domain.validators;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class BeforeNowValidator implements ConstraintValidator<NotBeforeNow, ZonedDateTime> {


    @Override
    public void initialize(NotBeforeNow constraintAnnotation) {
    }

    @Override
    public boolean isValid(ZonedDateTime zonedDateTime, ConstraintValidatorContext constraintValidatorContext) {

        if (zonedDateTime == null){
            return true;
        }

        boolean isAfter = zonedDateTime.isAfter(ZonedDateTime.now(ZoneId.of("UTC")));

        return isAfter;
    }
}

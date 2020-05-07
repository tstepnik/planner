package com.tstepnik.planner.domain;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy = {BeforeNowValidator.class})
@Target({METHOD,FIELD,CONSTRUCTOR,PARAMETER,ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface NotBeforeNow {

    String message() default "Planned date cannot be before actual date.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}

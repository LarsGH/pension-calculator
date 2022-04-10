package de.lasy.pension.validation.custom;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastYearsAgoValidator.class)
@Documented
public @interface AtLeastYearsAgo {

    String message() default "Muss mindestens {yearsAgo} Jahre her sein";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /** The number of years ago (at least). */
    int yearsAgo();

}
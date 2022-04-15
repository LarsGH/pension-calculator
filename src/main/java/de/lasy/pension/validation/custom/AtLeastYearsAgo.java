package de.lasy.pension.validation.custom;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation legt fest, dass ein Datum mindestens X Jahre her sein muss.
 */
@SuppressWarnings("SameReturnValue")
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastYearsAgoValidator.class)
@Documented
public @interface AtLeastYearsAgo {

    String message() default "{validation.atLeastYearsAgo}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /** Anzahl der Jahre, die ein Datum mindestens her sein soll. */
    int yearsAgo();

}
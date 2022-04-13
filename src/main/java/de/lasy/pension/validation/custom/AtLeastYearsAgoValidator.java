package de.lasy.pension.validation.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Validierung, dass ein Datum mindestens X Jahre her sein muss.
 */
class AtLeastYearsAgoValidator implements ConstraintValidator<AtLeastYearsAgo, LocalDate> {

    /** Anzahl der Jahre, die ein Datum mindestens her sein soll. */
    private int atLeastYearsAgo;

    @Override
    public void initialize(AtLeastYearsAgo constraintAnnotation) {
        this.atLeastYearsAgo = constraintAnnotation.yearsAgo();
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null) {
            return false;
        }

        LocalDate today = LocalDate.now();
        long yearsAgo = date.until(today, ChronoUnit.YEARS);
        return yearsAgo >= atLeastYearsAgo;
    }
}
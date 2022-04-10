package de.lasy.pension.validation.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class AtLeastYearsAgoValidator implements ConstraintValidator<AtLeastYearsAgo, LocalDate> {

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
        if (yearsAgo < atLeastYearsAgo) {
            return false;
        }

        return true;
    }
}
package de.lasy.pension.validation.custom.mock;

import de.lasy.pension.validation.custom.AtLeastYearsAgo;

import javax.validation.Payload;
import java.lang.annotation.Annotation;

public class AtLeastAgoMock implements AtLeastYearsAgo {

    private final int yearsAgo;

    public AtLeastAgoMock(int yearsAgo) {
        this.yearsAgo = yearsAgo;
    }

    @Override
    public String message() {
        return null;
    }

    @Override
    public Class<?>[] groups() {
        return new Class[0];
    }

    @Override
    public Class<? extends Payload>[] payload() {
        return new Class[0];
    }

    @Override
    public int yearsAgo() {
        return yearsAgo;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}

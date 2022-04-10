package de.lasy.pension.validation.custom;

import de.lasy.pension.validation.custom.mock.AtLeastAgoMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * Testet AtLeastAgoValidator.
 */
public class AtLeastYearsAgoValidatorTest {

    /** System under test. */
    private AtLeastYearsAgoValidator sut;

    private static final int TEN_YEARS = 10;
    private final AtLeastYearsAgo AT_LEAST_10_YEARS_AGO = new AtLeastAgoMock(TEN_YEARS);

    @BeforeEach
    public void setUp() {
        sut = new AtLeastYearsAgoValidator();
        sut.initialize(AT_LEAST_10_YEARS_AGO);
    }

    @Test
    public void isValidWithExactlyConfiguredYearsAgo() {
        LocalDate date = LocalDate.now().minusYears(TEN_YEARS);

        boolean isValid = sut.isValid(date, null);
        Assertions.assertThat(isValid).isTrue();
    }

    @Test
    public void isValidWithMoreThanConfiguredYearsAgo() {
        LocalDate date = LocalDate.now()
                .minusYears(TEN_YEARS)
                .minusDays(1);

        boolean isValid = sut.isValid(date, null);
        Assertions.assertThat(isValid).isTrue();
    }

    @Test
    public void isNotValidWithLessThanConfiguredYearsAgo() {
        LocalDate date = LocalDate.now()
                .minusYears(TEN_YEARS)
                .plusDays(1);

        boolean isValid = sut.isValid(date, null);
        Assertions.assertThat(isValid).isFalse();
    }

    @Test
    public void isNotValidWithNull() {
        boolean isValid = sut.isValid(null, null);
        Assertions.assertThat(isValid).isFalse();
    }
}

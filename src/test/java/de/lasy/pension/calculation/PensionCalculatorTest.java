package de.lasy.pension.calculation;

import de.lasy.pension.model.PensionCalculationInput;
import de.lasy.pension.model.PensionCalculationResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

/**
 * Testet PensionCalculator.
 */
@SpringBootTest
public class PensionCalculatorTest {

    @Autowired
    private PensionCalculator sut;

    @Test
    public void calculatedPensionAgeIs66ForThresholdYear() {
        LocalDate lastDayOfYear = getLastDayOfThresholdYear();

        int pensionAge = sut.calculatePensionAge(lastDayOfYear);
        Assertions.assertThat(pensionAge).isEqualTo(PensionCalculator.PENSION_66);
    }

    @Test
    public void calculatedPensionAgeIs66ForDateBeforeThreshold() {
        LocalDate year = getLastDayOfThresholdYear().minusDays(1);

        int pensionAge = sut.calculatePensionAge(year);
        Assertions.assertThat(pensionAge).isEqualTo(PensionCalculator.PENSION_66);
    }

    @Test
    public void calculatedPensionAgeIsEqualTo67ForDateAfterThreshold() {
        LocalDate year = getLastDayOfThresholdYear().plusDays(1);

        int pensionAge = sut.calculatePensionAge(year);
        Assertions.assertThat(pensionAge).isEqualTo(67);
    }

    @Test
    public void calculatedRetirementDateIsFirstOfMonthAfterReachingPensionAge() {
        PensionCalculationInput input = bornEndOfJanuary1900StartedToWorkWith16Earning100k();

        LocalDate retirementDate = sut.calculateRetirementDate(input);
        Assertions.assertThat(retirementDate).isEqualTo(LocalDate.of(1966, 2,1));
    }

    @Test
    public void calculatedPensionResult() {
        PensionCalculationInput input = bornEndOfJanuary1900StartedToWorkWith16Earning100k();

        PensionCalculationResult pensionResult = sut.calculatePension(input);

        Assertions.assertThat(pensionResult.getExpectedRetirementDate())
                .isEqualTo(LocalDate.of(1966, 2,1));
        // (100.000,00 / 1000) * 50 Arbeitsjahre
        Assertions.assertThat(pensionResult.getExpectedPension())
                .isEqualTo(5_000.0);
    }

    private PensionCalculationInput bornEndOfJanuary1900StartedToWorkWith16Earning100k() {
        LocalDate dayOfBirth = LocalDate.of(1900, 1, 31);
        LocalDate dayOfWorkStart = dayOfBirth.plusYears(16);
        return new PensionCalculationInput(dayOfBirth, dayOfWorkStart, 100_000.0);
    }

    private LocalDate getLastDayOfThresholdYear() {
        return LocalDate.of(PensionCalculator.PENSION_WITH_66_THRESHOLD_YEAR, 12,31);
    }
}

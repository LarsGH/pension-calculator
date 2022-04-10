package de.lasy.pension.calculation;

import de.lasy.pension.model.PensionCalculationInput;
import de.lasy.pension.model.PensionCalculationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class PensionCalculator {

    private static Logger logger = LoggerFactory.getLogger(PensionCalculator.class);

    @Value("${pensionCalculator.retirementAge:67}")
    private int retirementAge;

    public PensionCalculationResult calculatePension(PensionCalculationInput calculationInput) {
        logger.info("Calculating pension with input: {}", calculationInput);

        LocalDate retirementDate = calculateRetirementDate(calculationInput);
        long yearsWorked = calculationInput.getDateOfWorkStart().until(retirementDate, ChronoUnit.YEARS);
        // Bruttojahresgehalt / 1000 * Arbeitsjahre = Höhe der monatlichen Rente zum Rentenbeginn
        Double pension = calculationInput.getGrossIncome() / 1000 * yearsWorked;

        PensionCalculationResult result = new PensionCalculationResult(retirementDate, pension);
        logger.info("Calculated pension result: {}", result);
        return result;
    }

    LocalDate calculateRetirementDate(PensionCalculationInput calculationInput) {
        int pensionAge = calculatePensionAge(calculationInput.getDateOfBirth());

        // Rentenbeginn am ersten Tag des Folgemonats nach Erreichen der Altersgrenze
        LocalDate retirementDate = calculationInput.getDateOfBirth()
                .plus(pensionAge, ChronoUnit.YEARS)
                .plus(1, ChronoUnit.MONTHS)
                .withDayOfMonth(1);

        logger.debug("Calculated retirement date: {}", retirementDate);
        return retirementDate;
    }

    /** Grenzjahr für das Geburtsjahr, bis welches die Rente noch mit 66 Jahren möglich ist. */
    static final int PENSION_WITH_66_THRESHOLD_YEAR = 1958;
    /** Vorzeitiges Rentenalter für die Geburtsjahre älter gleich 1958 */
    static final int PENSION_66 = 66;

    int calculatePensionAge(LocalDate dateOfBirth) {
        int result = (dateOfBirth.getYear() <= PENSION_WITH_66_THRESHOLD_YEAR)
                ? PENSION_66
                : retirementAge;

        logger.debug("Calculated pension age: {}", result);
        return result;
    }
}

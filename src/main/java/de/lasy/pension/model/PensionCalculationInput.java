package de.lasy.pension.model;

import de.lasy.pension.validation.custom.AtLeastYearsAgo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * Input für die Berechnung der Rente.
 */
public class PensionCalculationInput {

    /** Bruttoeinkommen. */
    @AtLeastYearsAgo(yearsAgo = 16)
    private LocalDate dateOfBirth;

    /** Arbeitsbeginn. */
    @NotNull
    private LocalDate dateOfWorkStart;

    /** Brutto-Jahreseinkommen. */
    @Positive
    @NotNull()
    private Double grossIncome;

    public PensionCalculationInput(LocalDate dateOfBirth, LocalDate dateOfWorkStart, Double grossIncome) {
        this.dateOfBirth = dateOfBirth;
        this.dateOfWorkStart = dateOfWorkStart;
        this.grossIncome = grossIncome;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfWorkStart() {
        return dateOfWorkStart;
    }

    public void setDateOfWorkStart(LocalDate dateOfWorkStart) {
        this.dateOfWorkStart = dateOfWorkStart;
    }

    public Double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(Double grossIncome) {
        this.grossIncome = grossIncome;
    }

    @Override
    public String toString() {
        return "PensionCalculationInput{" +
                "dateOfBirth=" + dateOfBirth +
                ", dateOfWorkStart=" + dateOfWorkStart +
                ", grossIncome=" + grossIncome +
                '}';
    }
}

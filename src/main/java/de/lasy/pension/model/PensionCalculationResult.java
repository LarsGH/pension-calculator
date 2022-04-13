package de.lasy.pension.model;

import java.time.LocalDate;

/**
 * Das Ergebnis für die Rentenberechnung.
 */
public class PensionCalculationResult {
    /** Erwarteter Rentenbeginn. */
    private LocalDate expectedRetirementDate;

    /** Erwartete Monats-Rente (brutto). */
    private Double expectedPension;

    public PensionCalculationResult(LocalDate expectedRetirementDate, Double expectedPension) {
        this.expectedRetirementDate = expectedRetirementDate;
        this.expectedPension = expectedPension;
    }

    public Double getExpectedPension() {
        return expectedPension;
    }

    public void setExpectedPension(Double expectedPension) {
        this.expectedPension = expectedPension;
    }

    public LocalDate getExpectedRetirementDate() {
        return expectedRetirementDate;
    }

    public void setExpectedRetirementDate(LocalDate expectedRetirementDate) {
        this.expectedRetirementDate = expectedRetirementDate;
    }

    @Override
    public String toString() {
        return "PensionCalculationResult{" +
                "expectedRetirementDate=" + expectedRetirementDate +
                ", expectedPension=" + expectedPension +
                '}';
    }
}

package de.lasy.pension.validation.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Sammelt Validierungsfehler für eine Antwort an den Client.
 */
public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }
}

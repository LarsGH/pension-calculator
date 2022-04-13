package de.lasy.pension.validation;

import de.lasy.pension.validation.response.ValidationErrorResponse;
import de.lasy.pension.validation.response.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Behandlung von Validierungsfehlern.
 */
@ControllerAdvice
class ErrorHandlingControllerAdvice {

    /**
     * Gibt Validierungsfehler gesammelt zurück (sofern vorhanden).
     * @param e Exception, welche die fehlerhaften Attribute beinhaltet
     * @return Validierungsfehler
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            Violation violation = new Violation(fieldError.getField(), fieldError.getDefaultMessage());
            error.getViolations().add(violation);
        }
        return error;
    }

}

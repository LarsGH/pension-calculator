package de.lasy.pension.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.lasy.pension.model.PensionCalculationInput;
import de.lasy.pension.model.PensionCalculationResult;
import de.lasy.pension.validation.response.ValidationErrorResponse;
import de.lasy.pension.validation.response.Violation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

/**
 * Testet {@link PensionController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PensionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void validDataResponse() throws Exception {
        String requestJson = toJson(validInput());

        String expectedResult = toJson(new PensionCalculationResult(
                LocalDate.of(1966, 2, 1),
                5_000.0));

        performPostPensionCalculateWithContent(requestJson)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResult));
    }

    @Test
    public void invalidDataResponseForAgeLessThan16() throws Exception {
        PensionCalculationInput requestInput = validInput();
        requestInput.setDateOfBirth(LocalDate.now().minusYears(15));
        String requestJson = toJson(requestInput);

        ResultActions result = performPostPensionCalculateWithContent(requestJson);

        expectResponseWithValidationErrors(result,
                List.of(new Violation("dateOfBirth", "Muss mindestens 16 Jahre her sein")));
    }

    @Test
    public void invalidDataResponseForNullValues() throws Exception {
        PensionCalculationInput requestInput = validInput();
        requestInput.setDateOfBirth(null);
        requestInput.setDateOfWorkStart(null);
        requestInput.setGrossIncome(null);
        String requestJson = toJson(requestInput);

        ResultActions result = performPostPensionCalculateWithContent(requestJson);

        expectResponseWithValidationErrors(result,
                List.of(new Violation("dateOfBirth", "Muss mindestens 16 Jahre her sein"),
                        new Violation("dateOfWorkStart", "must not be null"),
                        new Violation("grossIncome", "must not be null")));
    }

    @Test
    public void invalidDataResponseForDateOfBirthInFuture() throws Exception {
        PensionCalculationInput requestInput = validInput();
        LocalDate futureDate = LocalDate.now().plusDays(1);
        requestInput.setDateOfWorkStart(futureDate);
        requestInput.setDateOfBirth(futureDate);
        String requestJson = toJson(requestInput);

        ResultActions result = performPostPensionCalculateWithContent(requestJson);

        expectResponseWithValidationErrors(result, List.of(
                new Violation("dateOfBirth", "Muss mindestens 16 Jahre her sein")));
    }

    @Test
    public void invalidDataResponseForNegativeIncome() throws Exception {
        PensionCalculationInput requestInput = validInput();
        requestInput.setGrossIncome(-1.0);
        String requestJson = toJson(requestInput);

        ResultActions result = performPostPensionCalculateWithContent(requestJson);

        expectResponseWithValidationErrors(result, List.of(
                new Violation("grossIncome", "must be greater than 0")));
    }

    private ResultActions performPostPensionCalculateWithContent(String requestJson) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders
                .post("/pension/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON));
    }

    private void expectResponseWithValidationErrors(ResultActions result, List<Violation> violations)
            throws Exception {
        String expectedResult = toJson(createResponseWithValidationErrors(violations));

        result.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(expectedResult));
    }

    private ValidationErrorResponse createResponseWithValidationErrors(List<Violation> violations) {
        ValidationErrorResponse errors = new ValidationErrorResponse();
        errors.getViolations().addAll(violations);

        return errors;
    }

    private String toJson(Object model) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            throw new AssertionError("Could not create JSON for input!", e);
        }
    }

    private PensionCalculationInput validInput() {
        LocalDate dayOfBirth = LocalDate.of(1900, 1, 31);
        LocalDate dayOfWorkStart = dayOfBirth.plusYears(16);
        return new PensionCalculationInput(dayOfBirth, dayOfWorkStart, 100_000.0);
    }


}
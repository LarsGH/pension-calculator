package de.lasy.pension.controller;

import de.lasy.pension.calculation.PensionCalculator;
import de.lasy.pension.model.PensionCalculationInput;
import de.lasy.pension.model.PensionCalculationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("pension")
public class PensionController {

    private static Logger logger = LoggerFactory.getLogger(PensionController.class);

    @Autowired
    private PensionCalculator calculator;

    public PensionController(PensionCalculator calculator) {
        this.calculator = calculator;
    }

    // TODO: Logging best practice?
    @PostMapping("/calculate")
    public PensionCalculationResult calculatePension(@Valid @RequestBody PensionCalculationInput calculationInput) {
        logger.info("pension/calculate requested with: {}", calculationInput);
        // TODO: Validierung und Error-Handling!
        PensionCalculationResult result = calculator.calculatePension(calculationInput);
        return result;
    }
}

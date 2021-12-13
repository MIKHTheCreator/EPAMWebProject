package com.epam.jwd.service.validator.input_validator;

import static com.epam.jwd.service.config.ValidatorConfig.AGE_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.DATE_FORMAT_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.NUMBER_PATTERN;

public class InputValidator {

    private static final InputValidator INSTANCE = new InputValidator();
    private static final int ZERO = 0;

    private InputValidator() {
    }

    public static InputValidator getInstance() {
        return INSTANCE;
    }

    public boolean isValidAgeFormat(String age) {
        return age.matches(AGE_PATTERN);
    }

    public boolean isEmptyString(String inputString) {
        return inputString.isBlank();
    }

    public boolean isZeroField(int inputAge) {
        return inputAge == ZERO;
    }

    public boolean isValidNumberFormat(String inputNumber) {
        return inputNumber.matches(NUMBER_PATTERN);
    }

    public boolean isValidDateFormat(String inputDate) {
        return inputDate.matches(DATE_FORMAT_PATTERN);
    }
}

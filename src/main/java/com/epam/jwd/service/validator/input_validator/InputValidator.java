package com.epam.jwd.service.validator.input_validator;

import static com.epam.jwd.service.config.ValidatorConfig.AGE_PATTERN;

public class InputValidator {

    private static final InputValidator INSTANCE = new InputValidator();

    private InputValidator() {
    }

    public static InputValidator getInstance() {
        return INSTANCE;
    }

    public boolean isValidAgeFormat(String age) {
        return age.matches(AGE_PATTERN);
    }
}

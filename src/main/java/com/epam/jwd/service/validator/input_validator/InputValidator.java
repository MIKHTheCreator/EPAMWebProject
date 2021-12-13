package com.epam.jwd.service.validator.input_validator;

import static com.epam.jwd.service.config.ValidatorConfig.AGE_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.DATE_FORMAT_PATTERN;
import static com.epam.jwd.service.config.ValidatorConfig.NUMBER_PATTERN;

/**
 * Validator class for input validation
 *
 * @author mikh
 * Class created as Singleton Pattern
 */
public class InputValidator {

    private static InputValidator instance = new InputValidator();
    private static final int ZERO = 0;

    private InputValidator() {
    }

    public static InputValidator getInstance() {
        if (instance == null) {
            instance = new InputValidator();
        }
        return instance;
    }

    /**
     * Method for checking age format
     *
     * @param age input age
     * @return true if age of valid format false otherwise
     */
    public boolean isValidAgeFormat(String age) {
        return age.matches(AGE_PATTERN);
    }

    /**
     * Method for checking input string for emptiness
     *
     * @param inputString input string
     * @return true if string is empty false otherwise
     */
    public boolean isEmptyString(String inputString) {
        return inputString.isBlank();
    }

    /**
     * Method for checking zero fields
     *
     * @param inputAge input field
     * @return true if input field is zero false otherwise
     */
    public boolean isZeroField(int inputAge) {
        return inputAge == ZERO;
    }

    /**
     * Method for checking input field for matching pattern
     *
     * @param inputNumber input number field
     * @return true if field is match pattern false otherwise
     */
    public boolean isValidNumberFormat(String inputNumber) {
        return inputNumber.matches(NUMBER_PATTERN);
    }

    /**
     * Method for checking if data format is valid
     *
     * @param inputDate input date
     * @return true if date is valid, false otherwise
     */
    public boolean isValidDateFormat(String inputDate) {
        return inputDate.matches(DATE_FORMAT_PATTERN);
    }
}

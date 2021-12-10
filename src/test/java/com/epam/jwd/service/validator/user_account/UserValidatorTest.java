package com.epam.jwd.service.validator.user_account;

import com.epam.jwd.service.dto.user_account.UserDTO;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.AGE_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.AGE_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.FIRST_NAME_LENGTH_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.FIRST_NAME_LENGTH_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.PHONE_NUMBER_MISS_MATCH;
import static com.epam.jwd.service.message.ExceptionMessage.PHONE_NUMBER_MISS_MATCH_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.SECOND_NAME_LENGTH_EXCEPTION;
import static com.epam.jwd.service.message.ExceptionMessage.SECOND_NAME_LENGTH_EXCEPTION_CODE;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserValidatorTest {

    private Validator<UserDTO, Integer> validator;
    private static final UserDTO USER_WITH_WRONG_FIRST_NAME = new UserDTO.Builder()
            .withFirstName("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk")
            .withSecondName("Kharevich")
            .withAge(50)
            .withPhoneNumber("375257536713")
            .build();
    private static final UserDTO USER_WITH_WRONG_SECOND_NAME = new UserDTO.Builder()
            .withFirstName("Mikhail")
            .withSecondName("gffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff")
            .withAge(50)
            .withPhoneNumber("375257536713")
            .build();
    private static final UserDTO USER_WITH_WRONG_PHONE_NUMBER = new UserDTO.Builder()
            .withFirstName("Mikhail")
            .withSecondName("Kharevich")
            .withAge(30)
            .withPhoneNumber("3752575367132")
            .build();
    private static final UserDTO USER_WITH_WRONG_AGE = new UserDTO.Builder()
            .withFirstName("Mikhail")
            .withSecondName("Kharevich")
            .withAge(-50)
            .withPhoneNumber("375257536713")
            .build();
    private static final UserDTO VALID_USER = new UserDTO.Builder()
            .withFirstName("Mikhail")
            .withSecondName("Kharevich")
            .withAge(20)
            .withPhoneNumber("375257536713")
            .build();

    @BeforeAll
    public void beforeAll() {
        this.validator = UserValidator.getInstance();
    }

    @Test
    void validateForWrongFirstName() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(USER_WITH_WRONG_FIRST_NAME));

        Assertions.assertEquals(FIRST_NAME_LENGTH_EXCEPTION + DELIMITER + FIRST_NAME_LENGTH_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongSecondName() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(USER_WITH_WRONG_SECOND_NAME));

        Assertions.assertEquals(SECOND_NAME_LENGTH_EXCEPTION + DELIMITER + SECOND_NAME_LENGTH_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongPhoneNumber() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(USER_WITH_WRONG_PHONE_NUMBER));

        Assertions.assertEquals(PHONE_NUMBER_MISS_MATCH + DELIMITER + PHONE_NUMBER_MISS_MATCH_CODE,
                exception.getMessage());
    }

    @Test
    void validateForWrongAge() {
        ServiceException exception = assertThrows(ServiceException.class, () -> validator.validate(USER_WITH_WRONG_AGE));

        Assertions.assertEquals(AGE_EXCEPTION + DELIMITER + AGE_EXCEPTION_CODE,
                exception.getMessage());
    }

    @Test
    void validateValidUser() {
        assertDoesNotThrow(() -> validator.validate(VALID_USER));
    }

}
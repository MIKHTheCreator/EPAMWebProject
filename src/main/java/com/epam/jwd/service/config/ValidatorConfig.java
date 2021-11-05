package com.epam.jwd.service.config;

public interface ValidatorConfig {

    Integer MAX_USERNAME_LENGTH = 32;
    Integer MIN_USERNAME_LENGTH = 3;
    String USERNAME_PATTERN = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    String EMAIL_PATTERN = "/(?:[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+" +
            "(?:\\.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*|\"(?:" +
            "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\" +
            "[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.)" +
            "{3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:" +
            "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])/gm";

    Integer MIN_PASSWORD_LENGTH = 8;
    Integer MAX_PASSWORD_LENGTH = 32;

    String PASSWORD_PATTERN = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";

    String PERSONAL_NUMBER_PATTERN = "^[3-6]{1}(0[1-9]|[12]\\d|3[01])(0[1-9]|1[1-9])\\d\\d[A-Z]{1}(\\d{3})(PP|PB)([0-9])$";
    String SERIA_AND_NUMBER_PATTERN = "^([A-Z]{2})(\\d{7})$";

    Integer MAX_FIRST_NAME_LENGTH = 40;
    Integer MIN_FIRST_NAME_LENGTH = 1;
    Integer MAX_SECOND_NAME_LENGTH = 60;
    Integer MIN_SECOND_NAME_LENGTH = 1;

    String PHONE_NUMBER_PATTERN = "^375(25|44|29|33)\\d{7}$";

    Integer MIN_AGE = 14;
    Integer MAX_AGE = 100;
}
package com.epam.jwd.service.message;

public interface ExceptionMessage {

    String SERVICE_SAVE_METHOD_EXCEPTION = "Save operation was failed";
    Integer SERVICE_SAVE_METHOD_EXCEPTION_CODE = 8;

    String SERVICE_FIND_ALL_METHOD_EXCEPTION = "Find all operation was failed";
    Integer SERVICE_FIND_ALL_METHOD_EXCEPTION_CODE = 9;

    String SERVICE_FIND_BY_ID_METHOD_EXCEPTION = "Find by id operation was failed";
    Integer SERVICE_FIND_BY_ID_METHOD_EXCEPTION_CODE = 10;

    String SERVICE_UPDATE_METHOD_EXCEPTION = "Update operation was failed";
    Integer SERVICE_UPDATE_METHOD_EXCEPTION_CODE = 11;

    String SERVICE_DELETE_METHOD_EXCEPTION = "Delete operation was failed";
    Integer SERVICE_DELETE_METHOD_EXCEPTION_CODE = 12;

    String USERNAME_LENGTH_EXCEPTION = "Username must be at least 3 symbols length and shorter than 32 symbols";
    Integer USERNAME_LENGTH_EXCEPTION_CODE = 13;

    String USERNAME_CORRESPOND_TO_PATTERN_EXCEPTION = "Username should contain special symbols form list: ($&+,:;=?@#|'<>.^*()%!-/)";
    Integer USERNAME_CORRESPOND_TO_PATTERN_EXCEPTION_CODE = 14;

    String UNSUPPORTED_EMAIL = "Such an email isn't valid";
    Integer UNSUPPORTED_EMAIL_CODE = 15;

    String PASSWORD_LENGTH_EXCEPTION = "Password must be at least 8 symbols length and shorter than 32 symbols";
    Integer PASSWORD_LENGTH_EXCEPTION_CODE = 16;

    String PASSWORD_CORRESPOND_TO_PASSWORD_PATTERN_EXCEPTION = "Password should contain Upper and Lower case letters, numbers and special characters";
    Integer PASSWORD_CORRESPOND_TO_PASSWORD_PATTERN_EXCEPTION_CODE = 17;

    String EXPIRATION_DATE_EXCEPTION = "The date must be valid";
    Integer EXPIRATION_DATE_EXCEPTION_CODE = 18;

    String PERSONAL_NUMBER_MISS_MATCH = "Wrong personal Number";
    Integer PERSONAL_NUMBER_MISS_MATCH_CODE = 19;

    String SERIA_AND_NUMBER_MISS_MATCH = "Wrong seria and number field";
    Integer SERIA_AND_NUMBER_MISS_MATCH_CODE = 20;

    String FIRST_NAME_LENGTH_EXCEPTION = "Name should be at least 1 symbol long and less than 40 symbols long";
    Integer FIRST_NAME_LENGTH_EXCEPTION_CODE = 21;

    String SECOND_NAME_LENGTH_EXCEPTION = "Second name should be at least 1 symbol long and less than 60 symbols long";
    Integer SECOND_NAME_LENGTH_EXCEPTION_CODE = 22;

    String PHONE_NUMBER_MISS_MATCH = "Wrong phone number format";
    Integer PHONE_NUMBER_MISS_MATCH_CODE = 23;

    String AGE_EXCEPTION = "Age can be in range [14; 100]";
    Integer AGE_EXCEPTION_CODE = 24;

    String CURRENCY_MISS_MATCH_EXCEPTION = "Wrong currency format";
    Integer CURRENCY_MISS_MATCH_EXCEPTION_CODE = 25;

    String INVALID_BALANCE_EXCEPTION = "Balance should be positive number";
    Integer INVALID_BALANCE_EXCEPTION_CODE = 26;

    String POSITIVE_PAYMENT_EXCEPTION = "Payment sum must be positive";
    Integer POSITIVE_PAYMENT_EXCEPTION_CODE = 27;

    String DATE_OF_PAYMENT_EXCEPTION = "Wrong date of payment";
    Integer DATE_OF_PAYMENT_EXCEPTION_CODE = 28;

    String PAYMENT_ORGANIZATION_EXCEPTION = "Wrong payment organization name";
    Integer PAYMENT_ORGANIZATION_EXCEPTION_CODE = 29;

    String PAYMENT_GOAL_EXCEPTION = "Wrong payment goal";
    Integer PAYMENT_GOAL_EXCEPTION_CODE = 30;

    String PIN_FORMAT_EXCEPTION = "Pin must contain 4 numbers";
    Integer PIN_FORMAT_EXCEPTION_CODE = 31;

    String CVV_CODE_EXCEPTION = "CVV code must contain 3 numbers";
    Integer CVV_CODE_EXCEPTION_CODE = 32;

    String FULL_NAME_EXCEPTION = "Full name may contain words, spaces, numbers and - with min length=1 and max length=72";
    Integer FULL_NAME_EXCEPTION_CODE = 33;

    String CREDIT_CARD_NUMBER_EXCEPTION = "Credit card number should contain only 16 numbers";
    Integer CREDIT_CARD_NUMBER_EXCEPTION_CODE = 34;

    String NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION = "You haven't got enough money to do this operation";
    Integer NOT_ENOUGH_MONEY_TO_PAY_EXCEPTION_CODE = 35;
}

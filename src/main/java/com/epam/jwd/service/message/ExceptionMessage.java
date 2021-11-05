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
}

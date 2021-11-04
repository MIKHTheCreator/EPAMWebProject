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

    String USERNAME_CONTAINS_SPECIAL_SYMBOLS_EXCEPTION = "Username shouldn't contain special symbols form list: ($&+,:;=?@#|'<>.^*()%!-/)";
    Integer USERNAME_CONTAINS_SPECIAL_SYMBOLS_EXCEPTION_CODE = 14;
}

package com.epam.jwd.dao.messages;

public interface ExceptionMessage {

    String DELIMITER = ":";
    String SAVE_EXCEPTION = "Saving data in data base was failed";
    Integer SAVE_EXCEPTION_CODE = 1;
    String ROLLBACK_EXCEPTION = "Rollback was failed";
    Integer ROLLBACK_EXCEPTION_CODE = 2;
    String FIND_ALL_EXCEPTION = "Find all method was failed";
    Integer FIND_ALL_EXCEPTION_CODE = 3;
}

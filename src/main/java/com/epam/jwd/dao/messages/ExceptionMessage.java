package com.epam.jwd.dao.messages;

public interface ExceptionMessage {

    String DELIMITER = ":";

    String SAVE_EXCEPTION = "Saving data in data base was failed";
    Integer SAVE_EXCEPTION_CODE = 1;

    String ROLLBACK_EXCEPTION = "Rollback was failed";
    Integer ROLLBACK_EXCEPTION_CODE = 2;

    String FIND_ALL_EXCEPTION = "Find all entities was failed";
    Integer FIND_ALL_EXCEPTION_CODE = 3;

    String FIND_BY_ID_EXCEPTION = "Find entity with current id was failed";
    Integer FIND_BY_ID_EXCEPTION_CODE = 4;

    String UPDATE_EXCEPTION = "Entity updating was failed";
    Integer UPDATE_EXCEPTION_CODE = 5;

    String DELETE_EXCEPTION = "Entity deleting was failed";
    Integer DELETE_EXCEPTION_CODE = 6;
}

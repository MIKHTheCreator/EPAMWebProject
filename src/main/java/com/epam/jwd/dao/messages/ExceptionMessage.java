package com.epam.jwd.dao.messages;

public interface ExceptionMessage {

    String INTERRUPTED_EXCEPTION_MESSAGE = "Thread was interrupted";
    String SAVE_OPERATION_EXCEPTION_MESSAGE = "Entity wasn't save correctly";
    String FIND_OPERATION_EXCEPTION_MESSAGE = "Can't find entity in database";
    String UPDATE_DATABASE_EXCEPTION_MESSAGE = "Can't update entity";
    String FIND_BY_ID_OPERATION_EXCEPTION_MESSAGE = "Can't find entity with such an id in database";
    String DELETE_ENTITY_EXCEPTION_MESSAGE = "Can't delete entity from database";
    String SQL_ROLLBACK_EXCEPTION_MESSAGE = "Can't rollback to the beginning state";
}

package com.epam.jwd.service.connection_pool_inicializer;

import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.jwd.dao.message.ExceptionMessage.DELIMITER;
import static com.epam.jwd.service.message.ExceptionMessage.CAN_NOT_INITIALIZED_CONNECTION_POOL_EXCEPTION_CODE;
import static com.epam.jwd.service.message.ExceptionMessage.CAN_NOT_INITIALIZE_CONNECTION_POOL_EXCEPTION;

/**
 * @author mikh
 * Class which initialize connection pool {@link ConnectionPool}
 * Class created by Singleton pattern
 */
public class ConnectionPoolInitializer {

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();
    private static final ConnectionPoolInitializer INSTANCE = new ConnectionPoolInitializer();

    private static final Logger log = LogManager.getLogger(ConnectionPoolInitializer.class);

    private ConnectionPoolInitializer() {
    }

    public static ConnectionPoolInitializer getInstance() {
        return INSTANCE;
    }

    /**
     * Method for initializing connectionPool
     *
     * @throws ServiceException if it's unable for create connection
     * @see ConnectionPool#init()
     */
    public void initPool() throws ServiceException {
        try {
            pool.init();
        } catch (DAOException e) {
            log.error(CAN_NOT_INITIALIZE_CONNECTION_POOL_EXCEPTION + DELIMITER + CAN_NOT_INITIALIZED_CONNECTION_POOL_EXCEPTION_CODE);
            throw new ServiceException(CAN_NOT_INITIALIZE_CONNECTION_POOL_EXCEPTION + DELIMITER + CAN_NOT_INITIALIZED_CONNECTION_POOL_EXCEPTION_CODE, e);
        }
    }

    /**
     * Method which shutDowns connection poll
     *
     * @throws ServiceException if it's unable for close connection
     * @see ConnectionPool#shutDown()
     */
    public void shutDown() throws ServiceException {
        try {
            pool.shutDown();
        } catch (DAOException e) {
            log.error(CAN_NOT_INITIALIZE_CONNECTION_POOL_EXCEPTION + DELIMITER + CAN_NOT_INITIALIZED_CONNECTION_POOL_EXCEPTION_CODE);
            throw new ServiceException(CAN_NOT_INITIALIZE_CONNECTION_POOL_EXCEPTION + DELIMITER + CAN_NOT_INITIALIZED_CONNECTION_POOL_EXCEPTION_CODE, e);
        }
    }
}

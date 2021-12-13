package com.epam.jwd.controller.command.response_context;

/**
 * Interface which provides response context for process method
 */
public interface ResponseContext {

    /**
     * Method that shows current path to the needed page
     * @return page path
     */
    String getPage();

    /**
     * Method which shows if the request should be redirected
     * @return true if request should be redirected false otherwise
     */
    boolean isRedirect();
}

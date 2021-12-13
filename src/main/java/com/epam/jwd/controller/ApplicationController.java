package com.epam.jwd.controller;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContextImpl;
import com.epam.jwd.service.connection_pool_inicializer.ConnectionPoolInitializer;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller class which provides main http method logic
 *
 * @author mikh
 */
@WebServlet(urlPatterns = "/bank")
public class ApplicationController extends HttpServlet {

    private final ConnectionPoolInitializer connectionPool = ConnectionPoolInitializer.getInstance();
    private static final String COMMAND_PARAMETER = "command";
    private static final String INIT_ERROR_MESSAGE = "Can't initialize the connectionPool";
    private static final String DESTROY_ERROR_MESSAGE = "Can't shutdown the connectionPool";

    private static final Logger log = LogManager.getLogger(ApplicationController.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     * @see ApplicationController#process(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
     * @see ApplicationController#process(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Method which describes main logic of taken command from request and gives it to precise command class
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    private void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String commandName = request.getParameter(COMMAND_PARAMETER);
        Command command = Command.of(commandName);
        ResponseContext commandResult = command.execute(new RequestContextImpl(request));

        if (commandResult.isRedirect()) {
            response.sendRedirect(commandResult.getPage());
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(commandResult.getPage());
            dispatcher.forward(request, response);
        }
    }

    /**
     * Method for initialization of connection pool within start of the application
     */
    @Override
    public void init() {
        try {
            connectionPool.initPool();
        } catch (ServiceException e) {
            log.error(INIT_ERROR_MESSAGE, e);
        }
    }

    /**
     * Method that shutDowns connection pool in the end of application work
     */
    @Override
    public void destroy() {
        super.destroy();
        try {
            connectionPool.shutDown();
        } catch (ServiceException e) {
            log.error(DESTROY_ERROR_MESSAGE, e);
        }
    }
}

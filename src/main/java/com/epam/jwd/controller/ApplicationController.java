package com.epam.jwd.controller;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.response_context.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContextImpl;
import com.epam.jwd.dao.connection_pool.api.ConnectionPool;
import com.epam.jwd.dao.connection_pool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/bank")
public class ApplicationController extends HttpServlet {

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private static final String COMMAND_PARAMETER = "command";
    private static final String INIT_ERROR_MESSAGE = "Can't initialize the connectionPool";
    private static final String DESTROY_ERROR_MESSAGE = "Can't shutdown the connectionPool";

    private static final Logger log = LogManager.getLogger(ApplicationController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

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

    @Override
    public void init() {
        try {
            connectionPool.init();
        } catch (DAOException e) {
            log.error(INIT_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            connectionPool.shutDown();
        } catch (DAOException e) {
            log.error(DESTROY_ERROR_MESSAGE, e);
        }
    }
}

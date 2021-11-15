package com.epam.jwd.controller;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.controller.request_context.RequestContextImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/bank")
public class ApplicationController extends HttpServlet {

    private static final String COMMAND_PARAMETER = "command";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
}

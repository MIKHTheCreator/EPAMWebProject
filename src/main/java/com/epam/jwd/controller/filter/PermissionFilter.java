package com.epam.jwd.controller.filter;

import com.epam.jwd.controller.command.Commands;
import com.epam.jwd.dao.entity.user_account.Role;
import com.epam.jwd.service.dto.user_account.UserDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static com.epam.jwd.dao.entity.user_account.Role.UNAUTHORIZED;

/**
 * Filter class which filter every servlet request for role identity
 */
@WebFilter(urlPatterns = "/*")
public class PermissionFilter implements Filter {

    private static final String ERROR_PAGE = "/bank?command=show_error_page_command";
    private static final String USER_ATTRIBUTE = "currentUser";
    private static final String COMMAND_ATTRIBUTE = "command";

    private final Map<Role, Set<Commands>> commandsByRole;

    public PermissionFilter() {
        this.commandsByRole = new EnumMap<>(Role.class);
    }

    /**
     * Main filter method which filter request or redirect to error page
     *
     * @param request     servlet request
     * @param response    servlet response
     * @param filterChain filter chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final Commands command = Commands.getCommands(req.getParameter(COMMAND_ATTRIBUTE));
        final HttpSession session = req.getSession(false);
        Role currentRole = extractRoleFromSession(session);
        final Set<Commands> allowedCommands = commandsByRole.get(currentRole);
        if (allowedCommands.contains(command)) {
            filterChain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect(ERROR_PAGE);
        }
    }

    /**
     * Method for adding allowed command to set
     *
     * @param filterConfig {@link FilterConfig}
     */
    @Override
    public void init(FilterConfig filterConfig) {

        for (Commands command : Commands.values()) {
            for (Role allowedRole : command.getAllowedRoles()) {
                Set<Commands> commands = commandsByRole.computeIfAbsent(allowedRole, k -> EnumSet.noneOf(Commands.class));
                commands.add(command);
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    /**
     * Method for extracting Role from session
     *
     * @param session current session
     * @return role of current user
     */
    private Role extractRoleFromSession(HttpSession session) {
        return session != null && session.getAttribute(USER_ATTRIBUTE) != null
                ? ((UserDTO) session.getAttribute(USER_ATTRIBUTE)).getRole()
                : UNAUTHORIZED;
    }
}

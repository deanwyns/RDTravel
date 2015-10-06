package com.realdolmen.rdtravel.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by JSTAX29 on 5/10/2015.
 */
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();

        /* TODO: authentication */
        boolean authenticated = request.getRemoteUser() != null && !request.getRemoteUser().isEmpty();

        if (authenticated) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        // redirect to login page
        response.sendRedirect(request.getContextPath() + "/login?url=" + URLEncoder.encode(uri, "UTF8"));


    }

    @Override
    public void destroy() {

    }
}

package com.example;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    private Set<String> availableTimezones;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        availableTimezones = Stream.of(TimeZone.getAvailableIDs())
                .collect(Collectors.toSet());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String timezone = httpRequest.getParameter("timezone");

        if (timezone != null && !timezone.isEmpty()) {
            if (!availableTimezones.contains(timezone)) {
                httpResponse.setContentType("text/html");
                httpResponse.getWriter().write("<html><body><h1>Invalid timezone</h1></body></html>");
                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Cookie timezoneCookie = new Cookie("lastTimezone", timezone);
            timezoneCookie.setMaxAge(30 * 24 * 60 * 60);
            timezoneCookie.setHttpOnly(true);
            httpResponse.addCookie(timezoneCookie);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

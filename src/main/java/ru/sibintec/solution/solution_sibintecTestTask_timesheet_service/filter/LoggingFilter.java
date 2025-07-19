package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр логирования входящих HTTP запросов.
 */
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
                                    ) throws ServletException, IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        log.info("Incoming request: {} {}", method, uri);
        try {
            filterChain.doFilter(request, response);
            if (response.getStatus() < 400) {
                log.info("Successful request: {} {} -> {}", method, uri, response.getStatus());
            } else {
                log.warn("Rejected request: {} {} -> {}", method, uri, response.getStatus());
            }
        } catch (Exception ex) {
            log.error("Failed request: {} {} -> {}", method, uri, ex.getMessage());
            throw ex;
        }
    }
}
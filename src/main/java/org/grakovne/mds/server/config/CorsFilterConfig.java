package org.grakovne.mds.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter for enabling CORS support.
 */
@Configuration
public class CorsFilterConfig extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.addHeader(
            "Access-Control-Allow-Origin",
            "*");

        response.addHeader(
            "Access-Control-Allow-Methods",
            "GET, " +
                "POST, " +
                "DELETE, " +
                "PUT, " +
                "PATCH, " +
                "HEAD, " +
                "OPTIONS");

        response.addHeader(
            "Access-Control-Allow-Headers",
            "Origin, " +
                "Accept, " +
                "X-Requested-With, " +
                "Content-Type, " +
                "Access-Control-Request-Method, " +
                "Access-Control-Request-Headers");

        response.addHeader(
            "Access-Control-Expose-Headers",
            "Access-Control-Allow-Origin, " +
                "Access-Control-Allow-Credentials");

        response.addHeader(
            "Access-Control-Allow-Credentials",
            "true");

        filterChain.doFilter(request, response);
    }
}
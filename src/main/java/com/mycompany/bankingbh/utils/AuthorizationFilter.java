/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankingbh.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Makintola
 */
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @Value("${bearer.token}")
    private String BEARER_TOKEN;  // Inject token from application.properties

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestPath = request.getRequestURI(); //get the current request route
        
        // Only apply the filter to paths starting with "/api"
        if (!requestPath.startsWith("/api")) {
            filterChain.doFilter(request, response); // Proceed to the next filter or controller
            return;
        }
        
        String authorizationHeader = request.getHeader("Authorization"); // retrieve authorization token from headers

        // Check if the Authorization header is missing
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json"); 
            //return unauthorized error message
            response.getWriter().write("{\"message\":\"Authorizationrequired to access endpoint\",\"status\":\"UNAUTHORIZED\",\"code\":401}");
            return;
        }

        String token = authorizationHeader.substring(7);  // Extract the token (after "Bearer ")

        // Validate the token
        if (token == null || !token.equals(BEARER_TOKEN)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Invalid authorization token\",\"status\":\"UNAUTHORIZED\",\"code\":401}");
            return;
        }

        filterChain.doFilter(request, response);  // Proceed to the next filter or controller
    }
}
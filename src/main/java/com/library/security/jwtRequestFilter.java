package com.library.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.library.service.JwtService;
import com.library.service.PatronService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class jwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final PatronService patronService;

    @Override
    @SuppressWarnings("null")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwtToken = requestHeader.substring(7);
        final String patronUsername = jwtService.extractUsername(jwtToken);
        if (patronUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = patronService.loadUserByUsername(patronUsername);
            if (jwtService.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}

package com.be001.cinevibe.filter;

import com.be001.cinevibe.model.Token;
import com.be001.cinevibe.service.JwtService;
import com.be001.cinevibe.service.TokenService;
import com.be001.cinevibe.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private final TokenService tokenService;
    private final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService, TokenService tokenService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            final String value = authorization.substring(7);

            try {
                String username = jwtService.extractUsername(value, true);

                if (username != null && SecurityContextHolder.getContext() == null) {
                    var userDetails = userService.findByUsername(username).userDetails();
                    Token token = tokenService.findByValue(value);
                    boolean isValidToken = !token.isExpired() && !token.isRevoked();

                    if (Boolean.TRUE.equals(isValidToken) && jwtService.isValidToken(value, true)) {
                        var authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}

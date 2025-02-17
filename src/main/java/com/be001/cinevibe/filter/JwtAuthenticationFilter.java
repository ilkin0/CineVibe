package com.be001.cinevibe.filter;

import com.be001.cinevibe.model.CustomUserDetails;
import com.be001.cinevibe.model.Token;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.service.JwtService;
import com.be001.cinevibe.service.TokenService;
import com.be001.cinevibe.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final UserService userService;

    private final TokenService tokenService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService, TokenService tokenService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String value = authHeader.substring(7);

        try {
            String username = jwtService.extractUsername(value, true);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
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
            log.error(e.getMessage());
        }


        filterChain.doFilter(request, response);
    }
}

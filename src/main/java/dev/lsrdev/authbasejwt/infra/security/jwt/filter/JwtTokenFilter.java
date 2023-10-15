package dev.lsrdev.authbasejwt.infra.security.jwt.filter;

import dev.lsrdev.authbasejwt.commons.exceptions.HttpStatusException;
import dev.lsrdev.authbasejwt.infra.exceptions.InvalidJwtTokenException;
import dev.lsrdev.authbasejwt.infra.security.jwt.provider.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        super();
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }  catch (HttpStatusException ex) {
            //this is very important, since it guarantees the user is not authenticated at all
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
            return;
        } catch (JwtException | InvalidJwtTokenException e) {
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}

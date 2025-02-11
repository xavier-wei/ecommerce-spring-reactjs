package com.gmail.merikbest2015.ecommerce.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


import java.io.IOException;

import static com.gmail.merikbest2015.ecommerce.constants.ErrorMessage.INVALID_JWT_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    private final Environment env; // 用於檢查 Spring Profile

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtProvider.resolveToken((HttpServletRequest) servletRequest);

        try {

            // 跳過測試環境的驗證
            if (isTestEnvironment()) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            if (token != null && jwtProvider.validateToken(token)) {
                Authentication authentication = jwtProvider.getAuthentication(token);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            throw new JwtAuthenticationException(INVALID_JWT_TOKEN);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private boolean isTestEnvironment() {
        String[] activeProfiles = env.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("test".equalsIgnoreCase(profile)) {
                return true;
            }
        }
        return false;
    }
}

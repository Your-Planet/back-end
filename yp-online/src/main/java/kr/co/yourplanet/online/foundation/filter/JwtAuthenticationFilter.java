package kr.co.yourplanet.online.foundation.filter;

import io.jsonwebtoken.JwtException;
import kr.co.yourplanet.online.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveAccessToken(request);
        SecurityContext context = SecurityContextHolder.getContext();

        try {
            if (context.getAuthentication() == null && jwtTokenProvider.validateAccessToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token.substring(7));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e){
            handleException(response, e);
            return;
        }

        filterChain.doFilter(request,response);
    }

    private void handleException(HttpServletResponse response, RuntimeException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"statusCode\":401, \"message\":\"%s\"}", e.getMessage()));
        response.setStatus(401);
        response.getWriter().flush();
    }
}

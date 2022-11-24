package com.ansv.taskmanagement.filter;

import com.ansv.taskmanagement.util.DataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestToken = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        String jwtToken = null;
        String username = null;

        if (DataUtils.notNullOrEmpty(requestToken)) {
            if (requestToken.startsWith("Bearer")) {
                jwtToken = requestToken.substring(7);
                username = jwtTokenProvider.getUsernameFromToken(requestToken);
            } else {
                logger.warn("JWT token does not begin with Bearer string");
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (DataUtils.notNull(authentication)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                logger.info("------SecurityContextHolder getPrincipal UserDetails:" + ((UserDetails) principal).getUsername());
            } else {
                logger.info("------SecurityContextHolder getPrincipal:" + principal);
            }
        }

//        get the token valid it
        if(DataUtils.notNullOrEmpty(username) && (DataUtils.notNull(authentication)) || "anonymousUser".equals((String)authentication.getPrincipal())) {
            // call from service in message bus
        }

        ContentCachingResponseWrapper responseCachingWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        filterChain.doFilter(request, response);
        // copy body to response
        responseCachingWrapper.copyBodyToResponse();

    }
}

package com.ansv.taskmanagement.filter;

import com.ansv.taskmanagement.dto.response.UserDTO;
import com.ansv.taskmanagement.handler.ApiError;
import com.ansv.taskmanagement.handler.GlobalRestExceptionHandler;
import com.ansv.taskmanagement.handler.authentication.JwtTokenNotValidException;
import com.ansv.taskmanagement.service.rabbitmq.RabbitMqReceiver;
import com.ansv.taskmanagement.service.rabbitmq.RabbitMqSender;
import com.ansv.taskmanagement.util.DataUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
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

    private final RabbitMqSender rabbitMqSender;
    private final RabbitMqReceiver rabbitMqReceiver;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, AuthenticationException {
        final String requestToken = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        String jwtToken = null;
        String username = null;
        UserDTO userDTO = new UserDTO();
        if (DataUtils.notNullOrEmpty(requestToken)) {
            if (requestToken.startsWith("Bearer")) {
                jwtToken = requestToken.substring(7);
                username = jwtTokenProvider.getUsernameFromToken(jwtToken);
            } else {
                logger.warn("JWT token does not begin with Bearer string");
            }

            if (DataUtils.notNullOrEmpty(username)) {
                // call from service in message bus
//                rabbitMqSender.sender(username);
//                userDTO = rabbitMqReceiver.userDTO;
//                Authentication authentication = jwtTokenProvider.get


            }

        }
        else {
            throw new JwtTokenNotValidException("JWT token not valid");
        }

        //        if (DataUtils.notNullOrEmpty(requestToken)) {
//            if (requestToken.startsWith("Bearer")) {
//                jwtToken = requestToken.substring(7);
//                username = jwtTokenProvider.getUsernameFromToken(jwtToken);
//            } else {
//                logger.warn("JWT token does not begin with Bearer string");
//            }
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            if (DataUtils.notNull(authentication)) {
//                Object principal = authentication.getPrincipal();
//                if (principal instanceof UserDetails) {
//                    logger.info("------SecurityContextHolder getPrincipal UserDetails:" + ((UserDetails) principal).getUsername());
//                } else {
//                    logger.info("------SecurityContextHolder getPrincipal:" + principal);
//                }
//            }
//
////        get the token valid it
//            if (DataUtils.notNullOrEmpty(username)) {
//                // call from service in message bus
//            }
//
//
//        }
//        else {
//            throw new JwtTokenNotValidException("JWT token not valid");
//
//        }


        ContentCachingResponseWrapper responseCachingWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        filterChain.doFilter(request, response);
        // copy body to response
        responseCachingWrapper.copyBodyToResponse();

    }
}

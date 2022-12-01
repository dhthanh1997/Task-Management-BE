package com.ansv.taskmanagement.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class JwtAuthenticationConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((auth) -> {

            try {
                auth.antMatchers("/**").permitAll().anyRequest().authenticated()
                        .and()
                        .httpBasic().and().cors().and().csrf().disable();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        return http.build();
    }


}

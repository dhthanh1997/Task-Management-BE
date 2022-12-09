package com.ansv.taskmanagement.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtAuthenticationConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((auth) -> {

            try {
                auth.antMatchers("/").permitAll().
                antMatchers("/**").permitAll()
                        .antMatchers("/taskManagement/api/**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .httpBasic().and().cors().and().csrf().disable();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsMappingConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*").allowedOriginPatterns("*")
                        .allowCredentials(true);
            }
        };
    }


}

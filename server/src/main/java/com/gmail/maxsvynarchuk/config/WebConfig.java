package com.gmail.maxsvynarchuk.config;

import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class WebConfig /* extends WebSecurityConfigurerAdapter */ {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http
//                .authorizeRequests(a -> a
//                        .antMatchers("/", "/error", "/webjars/**").permitAll()
//                        .anyRequest().authenticated())
//                .exceptionHandling(e -> e
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
//                .logout(l -> l
//                        .logoutSuccessUrl("/").permitAll())
//                .csrf(c -> c
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                )
//                .oauth2Login(
////                        o -> o
////                        .failureHandler((request, response, exception) -> {
////                            request.getSession().setAttribute("error.message", exception.getMessage());
////                            handler.onAuthenticationFailure(request, response, exception);
////                        })
//                );
//        // @formatter:on
//    }
}
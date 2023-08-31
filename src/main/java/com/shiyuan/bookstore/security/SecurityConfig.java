package com.shiyuan.bookstore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests(
                configurer -> configurer
                        // .requestMatchers("/books").hasAnyRole("CUSTOMER", "STAFF", "ADMIN")
                        .requestMatchers("/books/add").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/books/update").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/books/save").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/books/delete").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/orders/myorders/**").hasAnyRole("CUSTOMER")
                        .requestMatchers("/orders/manage/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/", "/books", "/images/**", "/css/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(
                        form -> form
                                .loginPage("/showLogin")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/", true)
                                .permitAll())
                .logout(logout -> logout.permitAll())
                .exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));
        return httpSecurity.build();

    }

}

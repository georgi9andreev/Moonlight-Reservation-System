package com.bootcamp3.MoonlightHotelAndSpa.configuration;

import com.bootcamp3.MoonlightHotelAndSpa.filter.CustomAccessDeniedHandler;
import com.bootcamp3.MoonlightHotelAndSpa.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.SecurityConstant.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtTokenFilter tokenFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfiguration(@Lazy JwtTokenFilter tokenFilter, CustomAccessDeniedHandler accessDeniedHandler) {
        this.tokenFilter = tokenFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers(PUBLIC_URLS).permitAll()
                        .antMatchers(PROTECTED_URLS).hasAnyAuthority(ADMIN)
                        .anyRequest().denyAll())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .csrf().disable();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

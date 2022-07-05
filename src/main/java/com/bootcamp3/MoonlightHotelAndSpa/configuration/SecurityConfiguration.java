package com.bootcamp3.MoonlightHotelAndSpa.configuration;

import com.bootcamp3.MoonlightHotelAndSpa.filter.CustomAccessDeniedHandler;
import com.bootcamp3.MoonlightHotelAndSpa.filter.JwtTokenFilter;
import com.bootcamp3.MoonlightHotelAndSpa.service.impl.UserServiceImpl;
import com.bootcamp3.MoonlightHotelAndSpa.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.SecurityConstant.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserServiceImpl userService;

    @Autowired
    public SecurityConfiguration(CustomAccessDeniedHandler accessDeniedHandler, JwtTokenUtil jwtTokenUtil, UserServiceImpl userService) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers(PUBLIC_URLS).permitAll()
                        .antMatchers(PROTECTED_URLS).permitAll()
                        .antMatchers(HttpMethod.POST, "/users").permitAll()
                        .antMatchers(HttpMethod.GET, "/users").hasAnyAuthority(ADMIN)
                        .anyRequest().denyAll())
                .addFilterBefore(new JwtTokenFilter(jwtTokenUtil, userService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .csrf().disable();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers(HttpMethod.POST, "/users")
                .antMatchers("/users/forgot");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

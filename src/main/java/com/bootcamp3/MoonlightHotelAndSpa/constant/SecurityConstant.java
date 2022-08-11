package com.bootcamp3.MoonlightHotelAndSpa.constant;

public class SecurityConstant {

    public static final Long JWT_TOKEN_VALIDITY = 10 * 60 * 60L;
    public static final String AUTHORITIES = "authorities";
    public static final String USERNAME = "username";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BLANK_USERNAME_OR_PASSWORD = "Username or password should not be empty.";
    public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";
    public static final String BEARER_PREFIX_MISSING = "JWT Token does not begin with Bearer String";
    public static final String TOKEN_EXPIRED = "JWT Token has expired";
    public static final String UNABLE_TO_GET_JWT = "Unable to get JWT Token";
    public static final String ADMIN = "ROLE_ADMIN";

    public static final String[] PUBLIC_URLS = {"/users/token", "/users/forgot", "/users/reset", "/rooms/{id}/reservations", "/v3/api-docs",
            "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/tables", "/tables/{id}/reservations", "/{id}/reservations"};
    public static final String[] PROTECTED_URLS = {"/users/*", "/rooms/*"};

    public static final String RANDOM_PASSWORD_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-+=/.,()[]{}!@#$%^&*abcdefghijklmnopqrstuvwxyz";
    public static final int RANDOM_PASSWORD_LENGTH = 10;
}

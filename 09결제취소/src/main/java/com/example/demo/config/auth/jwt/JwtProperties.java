package com.example.demo.config.auth.jwt;

/**
 * JWT 기본 설정값
 */
public class JwtProperties {
    public static final int EXPIRATION_TIME = 24*60*60;
    public static final String COOKIE_NAME = "JWT-AUTHENTICATION";
}
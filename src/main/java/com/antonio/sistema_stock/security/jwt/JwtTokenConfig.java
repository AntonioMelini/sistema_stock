package com.antonio.sistema_stock.security.jwt;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class JwtTokenConfig {
    public static final SecretKey SECRET_KEY  = Jwts.SIG.HS256.key().build(); //llave secreta segura
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
}

package com.antonio.sistema_stock.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils { //
        public static final SecretKey SECRET_KEY  = Jwts.SIG.HS256.key().build(); //llave secreta segura
        public static final String PREFIX_TOKEN = "Bearer ";
        public static final String HEADER_AUTHORIZATION = "Authorization";
        public static final String CONTENT_TYPE = "application/json";
        //@Value("${jwt.time.expiration}")
        public Long timeExpiration=3600000L;



    //generar el token de acceso
        public String generateAccesToken(String username){
            return Jwts.builder()
                    .subject(username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() +  timeExpiration))
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                    .compact();
        }



        //Obetener el username del token
    public String getUsernameFromToken(String token){
            return getClaim(token, Claims::getSubject); //esa funcion es la que entra en  Function<Claims,T >
    }


        //Obtener 1 solo claim
    public  <T> T getClaim(String token, Function<Claims, T> claimsFunction){
            Claims claims = extractAllTokens(token);
            return claimsFunction.apply(claims);
    }

        // Obtener todos los claims del token
        public Claims extractAllTokens(String token){
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        }

        //validar el token de acceso
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
           return true;
        } catch (Exception e) {
            return false;
        }

    }



}

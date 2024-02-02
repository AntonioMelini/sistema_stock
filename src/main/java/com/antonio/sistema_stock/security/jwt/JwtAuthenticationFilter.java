package com.antonio.sistema_stock.security.jwt;

import com.antonio.sistema_stock.entities.User;
import com.antonio.sistema_stock.security.models.SecurityUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.antonio.sistema_stock.security.jwt.JwtTokenConfig.*;

//ESTE FILTRO ES PARA AUTENTICAR Y GENERAR EL TOKEN NADA MAS

//UNA VEZ TENIENDO EL AUTHENTICATION,SUCCESFULLAUTHENTICATION Y UNSUCCESFULLAUTHENTICATION DEBEMOS CONFIGURAR ESTE FILTRO EN SPRING SECURITY

//FILTRO PARA HACER EL TOKEN Y DEVOLVERSELO AL CLIENTE
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter (AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("entro a authentication");

        User user = null;
        String password =null;
        String username = null;

        try {
            user= new ObjectMapper().readValue(request.getInputStream(), User.class);
            password= user.getPassword();
            username= user.getUsername();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        return authenticationManager.authenticate(authenticationToken); // el authenticate por detra llama al UserDetailsService
    }

    //ESTO ES CUANDO EL LOGIN FUE EXITOSO
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        System.out.println("entro a successfulAuthentication");
        SecurityUser user = (SecurityUser)  authResult.getPrincipal();
        String username = user.getUsername();

        Claims claims = Jwts.claims().add("authorities", authResult.getAuthorities()).build(); //le asignamos los roles

        String token = Jwts.builder()
                .subject(username)
                .claims(claims) //pasamos el role del usuario
                .expiration(new Date(System.currentTimeMillis() + 3600000)) //vale 1 hora
                .issuedAt(new Date()) //desde cuando es valido el token
                .signWith(SECRET_KEY) //con cual clave secreta firmar
                .compact(); // crea el jwt token
        response.addHeader(HEADER_AUTHORIZATION,PREFIX_TOKEN+ token); // agrega este header al response

        Map<String, String> body = new HashMap<>();
        body.put("token",token);
        body.put("username",username);
        body.put("message", String.format("Hola %s has iniciado sesion con exito! ", username));

        response.getWriter().write(new  ObjectMapper().writeValueAsString(body)); //pasa el token, el username  y un mensaje a la response
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);
    }

    //ESTO ES CUANDO EL LOGIN NO FUE EXCITOSO
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("entro a unsuccessfulAuthentication");
        Map<String, String> body = new HashMap<>();
        body.put("Message", "Error en la autenticacion username o password incorrecto!");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }
}

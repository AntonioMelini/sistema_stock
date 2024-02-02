package com.antonio.sistema_stock.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


import static com.antonio.sistema_stock.security.jwt.JwtTokenConfig.*;

//ESTE FILTRO VALIDA QUE EL JWT SEA VALIDO
public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
      try {


          System.out.println("entro a JwtValidationFilter");



          String header = request.getHeader(HEADER_AUTHORIZATION);

          if (header == null || header.isBlank() || !header.startsWith(PREFIX_TOKEN)) {
              //chain.doFilter(request, response);
              throw new SignatureException("No cumple con el Header authorization establecido");
          }

          String token = header.replace(PREFIX_TOKEN, "");

          Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload(); //verifica el token y obtiene el payload
          String username = claims.getSubject();
          String authoritiesClaims = claims.get("authorities").toString();


          Collection<? extends GrantedAuthority> authorities = Arrays.stream(authoritiesClaims.concat(",").split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);//la password solo se pasa cuando hay que verificar en el filtro

          SecurityContextHolder.getContext().setAuthentication(authenticationToken);

          chain.doFilter(request, response); // chain es el fileter chain , do filter, continua con los siguientes filtros
      }catch (SignatureException | MalformedJwtException e){
          System.out.println("entro a error del validFilter");
          Map<String, String> body = new HashMap<>();
          body.put("Message", e.getMessage());
          body.put("error", "JWT token es invalido!");

          response.getWriter().write(new ObjectMapper().writeValueAsString(body));
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          response.setContentType(CONTENT_TYPE);
      }


    }
}

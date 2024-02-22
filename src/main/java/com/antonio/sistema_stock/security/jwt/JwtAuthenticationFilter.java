package com.antonio.sistema_stock.security.jwt;

import com.antonio.sistema_stock.entities.User;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//UsernamePasswordAuth.. nos ayuda a authenticar personas en el server

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils){
        this.jwtUtils=jwtUtils;
    }
    @Override  //intenta autenticarse, 1) agarrar el user de nuevo
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("ENTRO A AUTHENTICATION");
        User user =null;
        String username="";
        String password="";
        try {
            // el request viene como JSON y lo tenemos que convertir en Objeto

            user = new ObjectMapper().readValue(request.getInputStream(), User.class); // toma la password y user del request
            username = user.getUsername();
            password = user.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // con este token nos autenticamos en la app
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);

//AuthenticationManager: Es el objete que se encarga de la autenticacion
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        //si la authenticacion es correcta se llama al metodo successfulAuthentication
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

       //Obtener detalles del usuario

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal(); // detalles del user

        String acces_token = jwtUtils.generateAccesToken(user.getUsername(),authResult); //genera un token por haberse autenticado con exito
        String refresh_token = jwtUtils.generateRefreshToken(user.getUsername());

        response.addHeader("Authorization",acces_token);
        response.addHeader("refresh_token",refresh_token);
        Map<String, String>body =new HashMap<>();
        body.put("username",user.getUsername());
        body.put("message", String.format("Hola %s has iniciado sesion con exito! ", user.getUsername()));
        body.put("access_token",acces_token);
        body.put("refresh_token",refresh_token);
        body.put("roles",user.getAuthorities().toString().replace("[","").replace("]",""));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body)); //escribimos el Map como un Json en la respuesta
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //le damos el tipo de dato que se devuelve como respuesta
        response.getWriter().flush();  //se asegura que todo se escriba correctamente
        //aca devolvemos una response con el token y informacion


        super.successfulAuthentication(request, response, chain, authResult);
    }

}








































//ESTE FILTRO ES PARA AUTENTICAR Y GENERAR EL TOKEN NADA MAS

//UNA VEZ TENIENDO EL AUTHENTICATION,SUCCESFULLAUTHENTICATION Y UNSUCCESFULLAUTHENTICATION DEBEMOS CONFIGURAR ESTE FILTRO EN SPRING SECURITY

//FILTRO PARA HACER EL TOKEN Y DEVOLVERSELO AL CLIENTE


/*
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
        System.out.println("ESTA SON LAS AUTORIDADES DEL USUARIO: "+ authResult.getAuthorities());
        String username = user.getUsername();

        Claims claims = Jwts.claims().add("role", authResult.getAuthorities()).build(); //le asignamos los roles

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


 */
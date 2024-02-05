package com.antonio.sistema_stock.security.jwt;


import com.antonio.sistema_stock.security.services.UserDetailsServiceImpl;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;





//una vez por peticion se va a validar el token
@Component
public class JwtValidationFilter extends BasicAuthenticationFilter {

     private JwtUtils jwtUtils; //para validar token


    private UserDetailsServiceImpl userDetailsService; //para saber el user en la db

    public JwtValidationFilter(AuthenticationManager authenticationManager ,JwtUtils jwtUtils,UserDetailsServiceImpl userDetailsService) {
        super(authenticationManager);
        this.jwtUtils=jwtUtils;
        this.userDetailsService=userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        System.out.println("ya entro a validacion");
        //1) extraer el token

        String tokenHeader = request.getHeader("Authorization");

        //validamos el header
        if (tokenHeader != null || tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.replace("Bearer ","");
          if(jwtUtils.isTokenValid(token)){
              String username = jwtUtils.getUsernameFromToken(token); // obtenemos user del token
              UserDetails userDetails = userDetailsService.loadUserByUsername(username); //obtiene el user de la base de datos con sus datos y sus roles

              UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities()); // autenticar al useruario

              // contiene la autenticacion propia de la app
              SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          }
        }
        filterChain.doFilter(request, response);

    }



}




/*

//ESTE FILTRO VALIDA QUE EL JWT SEA VALIDO
@Component
public class JwtValidationFilter extends BasicAuthenticationFilter {
    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

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

          UserDetails userDetails = UserDetailsService.loadUserByUsername(username);



          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());//la password solo se pasa cuando hay que verificar en el filtro

          SecurityContextHolder.getContext().setAuthentication(authenticationToken);

          chain.doFilter(request, response); // chain es el fileter chain , do filter, continua con los siguientes filtros

/*

          Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload(); //verifica el token y obtiene el payload
          String username = claims.getSubject();
          String authoritiesClaims = claims.get("role").toString();
          System.out.println("Esto es lo que agarra del token: "+authoritiesClaims);


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

*/
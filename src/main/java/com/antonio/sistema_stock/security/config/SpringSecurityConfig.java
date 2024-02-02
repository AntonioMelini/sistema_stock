package com.antonio.sistema_stock.security.config;

import com.antonio.sistema_stock.security.jwt.JwtAuthenticationFilter;
import com.antonio.sistema_stock.security.jwt.JwtValidationFilter;
import com.antonio.sistema_stock.security.services.JpaUserDetailsService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        System.out.println("entrooooooo aca filterchain");
        try {


            return http.authorizeHttpRequests((authz) -> authz
                            .requestMatchers(HttpMethod.POST, "/login").permitAll()
                            .anyRequest().authenticated()) //todas las request requieren authenticacion
                            .csrf(config -> config.disable()) //token, valor secreto unico que se genera del lado del servidor. Evitar vulneravilidad desabilitando
                            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // La sesion HTTP, donde se guarda la sesion del usuario, es por defecto con ESTADO, al hacerlo STATELESS por cada peticion hay que mandar el JWT.
                            .userDetailsService(userDetailsService)
                            .addFilter(new JwtAuthenticationFilter(authenticationManager())) //agruega el filtro del token
                            .addFilter(new JwtValidationFilter(authenticationManager()))
                            .build();

        }catch (Exception e){
            System.out.println("va queriendo");
            throw new Exception(e.getMessage());
        }
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
     /*

                            .requestMatchers(HttpMethod.POST,    "/api/v1/user").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,    "/api/v1/user/inactive").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,    "/api/v1/user/cuit/*").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,    "/api/v1/user/businessName/*").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST,    "/api/v1/user/register").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,    "/api/v1/user/*").hasRole("ADMIN")

                 */
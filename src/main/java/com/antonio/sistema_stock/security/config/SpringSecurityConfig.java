package com.antonio.sistema_stock.security.config;

import com.antonio.sistema_stock.security.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SpringSecurityConfig {

    @Autowired
    private JpaUserDetailsService userDetailsService;
    @Bean
    SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        System.out.println("entrooooooo aca fefe");
        return http.authorizeHttpRequests(  (authz) -> authz
                        .anyRequest().authenticated()) //todas las request requieren authenticacion
                        .csrf(config -> config.disable()) //token, valor secreto unico que se genera del lado del servidor. Evitar vulneravilidad desabilitando
                        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // La sesion HTTP, donde se guarda la sesion del usuario, es por defecto con ESTADO, al hacerlo STATELESS por cada peticion hay que mandar el JWT.
                        .userDetailsService(userDetailsService)
                        .httpBasic(Customizer.withDefaults()) //genera la consola en la web para autenticarse, o el basic auth en postman
                        .build();


    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
     /*
                        .requestMatchers(HttpMethod.POST,    "/api/v1/user/register").permitAll() //permite crear usuarios sin estar authorizado
                        .requestMatchers(HttpMethod.POST,    "/api/v1/user").permitAll() //permite crear admins sin estar authorizado
                        .requestMatchers(HttpMethod.POST,    "/api/v1/role/register").permitAll() //permite crear roles sin estar authorizado
                        .requestMatchers(HttpMethod.GET,    "/api/v1/user").permitAll() //permite ver todos los usuarios sin estar autorizados

                 */
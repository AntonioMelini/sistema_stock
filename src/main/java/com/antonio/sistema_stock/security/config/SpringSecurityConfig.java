package com.antonio.sistema_stock.security.config;


import com.antonio.sistema_stock.security.jwt.JwtAuthenticationFilter;
import com.antonio.sistema_stock.security.jwt.JwtUtils;
import com.antonio.sistema_stock.security.jwt.JwtValidationFilter;
import com.antonio.sistema_stock.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,AuthenticationManager authenticationManager) throws Exception { //metodo que configura la cadena de filtros, seguridad
        System.out.println("entrooooooo  filterchain");
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        JwtValidationFilter jwtValidationFilter = new JwtValidationFilter(authenticationManager,jwtUtils,userDetailsService);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);

        //jwtAuthenticationFilter.setFilterProcessesUrl("/logiun"); CAMBIA LA URL DEL login
        try {


            return http
                    .csrf(AbstractHttpConfigurer::disable) //token, valor secreto unico que se genera del lado del servidor. Evitar vulneravilidad desabilitando
                    .authorizeHttpRequests(authz -> {
                        authz.requestMatchers("/login").permitAll();
                        authz.anyRequest().authenticated();
                    })//todas las request requieren authenticacion
                    .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // La sesion HTTP, donde se guarda la sesion del usuario, es por defecto con ESTADO, al hacerlo STATELESS por cada peticion hay que mandar el JWT.
                    .addFilter(jwtAuthenticationFilter) //se valida el token
                    .addFilter(jwtValidationFilter) // primero el nuestro despues el de spring security
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .build();

        } catch (Exception e) {
            System.out.println("va queriendo");
            throw new Exception(e.getMessage());
        }
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    AuthenticationManager authenticationManager (HttpSecurity httpSecurity) throws Exception { //objeto que se encarga de la autenticacion de la app
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }


   @Bean
    CorsConfigurationSource corsConfigurationSource (){
       CorsConfiguration corsConfiguration= new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT"));
        corsConfiguration.setAllowCredentials(true);

       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", corsConfiguration);
       return source;
   }

   @Bean
    FilterRegistrationBean<CorsFilter> corseFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource())
        );
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
   }

}



































/*

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
                            .requestMatchers(HttpMethod.POST,    "/api/v1/user").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,    "/api/v1/user").hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.GET,    "/api/v1/user/inactive").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,    "/api/v1/user/cuit/*").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,    "/api/v1/user/businessName/*").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST,    "/api/v1/user/register").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,    "/api/v1/user/*").hasRole("ADMIN")
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


 */
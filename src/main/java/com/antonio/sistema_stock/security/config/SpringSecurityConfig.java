package com.antonio.sistema_stock.security.config;


import com.antonio.sistema_stock.security.jwt.JwtAuthenticationFilter;
import com.antonio.sistema_stock.security.jwt.JwtUtils;
import com.antonio.sistema_stock.security.jwt.JwtValidationFilter;
import com.antonio.sistema_stock.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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


    /*
    @Bean
    protected AuthenticationManager configure (AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
         authenticationManagerBuilder.userDetailsService(userDetailsService).configure(authenticationManagerBuilder);
         return authenticationManagerBuilder.build();

    }

     */

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
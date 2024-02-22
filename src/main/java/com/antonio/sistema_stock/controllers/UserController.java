package com.antonio.sistema_stock.controllers;

import com.antonio.sistema_stock.dto.dtoRequest.UserDtoRequest;
import com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse;
import com.antonio.sistema_stock.entities.User;
import com.antonio.sistema_stock.security.jwt.JwtUtils;
import com.antonio.sistema_stock.services.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.antonio.sistema_stock.security.jwt.JwtUtils.CONTENT_TYPE;
import static com.antonio.sistema_stock.security.jwt.JwtUtils.SECRET_KEY;


@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private  IUserService userService;
    @Autowired
    private JwtUtils jwtUtils;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/inactive")
    public ResponseEntity<?> getAllInactive(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllInactive());

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cuit/{cuit}")
    public ResponseEntity<?> getByCuit(@PathVariable String cuit){

            return ResponseEntity.status(HttpStatus.OK).body(userService.getByCuit(cuit));

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/businessName/{name}")
    public ResponseEntity<?> getByBusinessName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByBusinessName(name));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> insert(@RequestBody @Valid UserDtoRequest userDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.insert(userDtoRequest));
    }

/*
    @PostMapping("/register")
    private ResponseEntity<?> registerAdmin(@Valid @RequestBody UserDtoRequest userDtoRequest){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerAdmin(userDtoRequest));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

 */


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/disable/{cuit}")
    public  ResponseEntity<?> disableByCuit(@PathVariable String cuit){
        return ResponseEntity.status(HttpStatus.OK).body(userService.disableByCuit(cuit));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{cuit}")
    public ResponseEntity<?> deleteByCuit( @PathVariable String cuit) throws Exception {
        System.out.println("entro al controller");
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteByCuit(cuit));
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/info/{jwt}")
    public ResponseEntity<?> getUserInfo (@PathVariable String jwt) throws Exception {
        try{
            if(jwt.isBlank() ) throw new Exception("ERROR EN EL TOKEN");
            System.out.println(jwt);
            String username = jwtUtils.getUsernameFromToken(jwt);
            User user= userService.findByUsername(username).orElseThrow();
            Map <String , String> response = new HashMap<>();
            response.put("username",username);
            response.put("cuit",user.getCuit());
            response.put("business_direction",user.getBusiness_direction());
            response.put("business_name",user.getBusiness_name());
            response.put("email",user.getEmail());
            response.put("gross_income",user.getGross_income());
            response.put("roles",user.getRole());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/token/refresh")
    public void refreshToken( HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        try {
            System.out.println(request.getHeader("Authorization"));
            String refresh_token_Header = request.getHeader("Authorization");
            if (refresh_token_Header != null || refresh_token_Header.startsWith("Bearer ")) {
                String refersh_token_old = refresh_token_Header.replace("Bearer ", "");
                if (jwtUtils.isTokenValid(refersh_token_old)) {
                    String username = jwtUtils.getUsernameFromToken(refersh_token_old);
                    User user=userService.findByUsername(username).orElseThrow();
                    Long accessTokenExp = 1200000L;
                   // String[] roles = userService.findByUsername(username).orElseThrow(Exception::new).getRole().concat(",").split(",");
                   // System.out.println("ESto son los roles: "+ Arrays.stream(roles).toList());




                    String acces_token = Jwts.builder()
                            .subject(user.getUsername())
                            .expiration(new Date(System.currentTimeMillis() +accessTokenExp ))
                            .issuedAt(new Date(System.currentTimeMillis()))
                            .claim("roles", Collections.singletonList(user.getRole()))
                            .signWith(SECRET_KEY)
                            .compact();
                    String refresh_token = Jwts.builder()
                            .subject(user.getUsername())
                            .expiration(new Date(System.currentTimeMillis() +3600000L ))
                            .issuedAt(new Date(System.currentTimeMillis()))
                            .signWith(SECRET_KEY)
                            .compact();

                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", acces_token);
                    tokens.put("refresh_token", refresh_token);
                    response.setStatus(200);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),tokens);

                }
            }

        } catch (Exception e) {
            System.out.println("entro a unsuccessfulAuthentication");
            Map<String, String> body = new HashMap<>();
            body.put("Message", "Error en la autenticacion username o password incorrecto!");
            body.put("error", e.getMessage());

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(401);
            response.setContentType(CONTENT_TYPE);
           // throw new Exception(e);

        }
    }
/*

 */





}



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


      return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfo(jwt));



    }

    @GetMapping("/token/refresh")
    public void refreshToken( HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        userService.refreshToken(request,response);



    }
/*

 */





}



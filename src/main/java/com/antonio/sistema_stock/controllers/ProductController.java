package com.antonio.sistema_stock.controllers;

import jakarta.persistence.GeneratedValue;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {


   // @PreAuthorize("hasAuthority('ROLE_USER')")
    /*
    @PostMapping("")
    public ResponseEntity<?> deleteByCuit() {
        return ResponseEntity.status(HttpStatus.CREATED).body();
    }

     */

    @GetMapping("")
    public String getToken(HttpServletRequest request) throws Exception
    {
        String user = request.getHeader(HttpHeaders.AUTHORIZATION);

        return user;
    }

}

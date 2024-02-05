package com.antonio.sistema_stock.controllers;

import com.antonio.sistema_stock.dto.dtoRequest.ProductDtoRequest;
import com.antonio.sistema_stock.services.IProductService;
import jakarta.persistence.GeneratedValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private IProductService productService;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/product/{username}")
    public ResponseEntity<?> insert(@Valid @RequestBody ProductDtoRequest product,@PathVariable String username) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.insert(product,username));
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/products/{username}")
    public ResponseEntity<?> getAll(@PathVariable String username) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAll(username));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getById( @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.getById(id));
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateById( @PathVariable Long id, @RequestBody ProductDtoRequest productDtoRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.updateById(id,productDtoRequest));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteById( @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.deleteById(id));
    }





}

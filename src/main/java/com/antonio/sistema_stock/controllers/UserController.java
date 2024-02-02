package com.antonio.sistema_stock.controllers;

import com.antonio.sistema_stock.dto.dtoRequest.UserDtoRequest;
import com.antonio.sistema_stock.exceptions.user.UserCreateValidation;
import com.antonio.sistema_stock.services.IUserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private  IUserService userService;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());

    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/inactive")
    public ResponseEntity<?> getAllInactive(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllInactive());

    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/cuit/{cuit}")
    public ResponseEntity<?> getByCuit(@PathVariable String cuit){

            return ResponseEntity.status(HttpStatus.OK).body(userService.getByCuit(cuit));

    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/businessName/{name}")
    public ResponseEntity<?> getByBusinessName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByBusinessName(name));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{cuit}")
    public  ResponseEntity<?> deleteByCuit(@PathVariable String cuit){
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteByCuit(cuit));
    }


}



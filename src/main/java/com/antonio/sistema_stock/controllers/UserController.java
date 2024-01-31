package com.antonio.sistema_stock.controllers;

import com.antonio.sistema_stock.dto.dtoRequest.UserDtoRequest;
import com.antonio.sistema_stock.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private  IUserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());

    }
    @GetMapping("/inactive")
    public ResponseEntity<?> getAllInactive(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllInactive());

    }
    @GetMapping("/cuit/{cuit}")
    public ResponseEntity<?> getByCuit(@PathVariable String cuit){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getByCuit(cuit));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @GetMapping("/businessName/{name}")
    public ResponseEntity<?> getByBusinessName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByBusinessName(name));
    }



    @PostMapping("")
    public ResponseEntity<?> insert(@Valid @RequestBody UserDtoRequest userDtoRequest){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.insert(userDtoRequest));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    @DeleteMapping("/{cuit}")
    public  ResponseEntity<?> deleteByCuit(@PathVariable String cuit){
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteByCuit(cuit));
    }


}

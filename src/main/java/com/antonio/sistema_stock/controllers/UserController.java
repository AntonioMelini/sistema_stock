package com.antonio.sistema_stock.controllers;

import com.antonio.sistema_stock.models.dtoRequest.UserDtoRequest;
import com.antonio.sistema_stock.models.dtoResponse.UserDtoResponse;
import com.antonio.sistema_stock.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private  IUserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(userService.getAll());

    }
    @GetMapping("/inactive")
    public ResponseEntity<?> getAllInactive(){
        return ResponseEntity.ok().body(userService.getAllInactive());

    }
    @GetMapping("/cuit/{cuit}")
    public ResponseEntity<?> getByCuit(@PathVariable String cuit){
        return ResponseEntity.ok().body(userService.getByCuit(cuit));
    }
    @GetMapping("/businessName/{name}")
    public ResponseEntity<?> getByBusinessName(@PathVariable String name){
        return ResponseEntity.ok().body(userService.getByBusinessName(name));
    }



    @PostMapping("")
    public ResponseEntity<?> insert(@RequestBody UserDtoRequest userDtoRequest){
        try {
            return ResponseEntity.ok().body(userService.insert(userDtoRequest));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @DeleteMapping("/{cuit}")
    public  ResponseEntity<?> deleteByCuit(@PathVariable String cuit){
        return ResponseEntity.ok().body(userService.deleteByCuit(cuit));
    }
}

package com.antonio.sistema_stock.controllers;

import com.antonio.sistema_stock.models.dto.UserDto;
import com.antonio.sistema_stock.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private  IUserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(userService.getAll());

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
    public ResponseEntity<?> insert(@RequestBody UserDto userDto){
        try {
            return ResponseEntity.ok().body(userService.insert(userDto));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @DeleteMapping("/{cuit}")
    public  ResponseEntity<?> deleteByCuit(@PathVariable String cuit){
        return ResponseEntity.ok().body(userService.deleteByCuit(cuit));
    }
}

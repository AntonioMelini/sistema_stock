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
    @GetMapping("/saludar")
    public String saludo(){
        return "hola que tal";
    }


    @PostMapping("")
    public ResponseEntity<?> insert(@RequestBody UserDto userDto){
        return ResponseEntity.ok().body(userService.insert(userDto));
    }
}

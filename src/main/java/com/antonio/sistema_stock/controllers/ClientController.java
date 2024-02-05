package com.antonio.sistema_stock.controllers;

import com.antonio.sistema_stock.dto.dtoRequest.ClientDtoRequest;
import com.antonio.sistema_stock.entities.Client;
import com.antonio.sistema_stock.services.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ClientController {


        @Autowired
        private IClientService clientService;


        @PreAuthorize("hasRole('USER')")
        @PostMapping("/client/{username}")
        public ResponseEntity<?> insert(@Valid @RequestBody ClientDtoRequest client, @PathVariable String username) throws Exception {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.insert(client,username));
        }
        @PreAuthorize("hasRole('USER')")
        @GetMapping("/clients/{username}")
        public ResponseEntity<?> getAll(@PathVariable String username) throws Exception {
            return ResponseEntity.status(HttpStatus.OK).body(clientService.getAll(username));
        }

        @PreAuthorize("hasRole('USER')")
        @GetMapping("/client/{id}")
        public ResponseEntity<?> getById( @PathVariable Long id) throws Exception {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.getById(id));
        }
        @PreAuthorize("hasRole('USER')")
        @PutMapping("/client/{id}")
        public ResponseEntity<?> updateById( @PathVariable Long id, @RequestBody ClientDtoRequest client) throws Exception {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.updateById(id,client));
        }

        @PreAuthorize("hasRole('USER')")
        @DeleteMapping("/client/{id}")
        public ResponseEntity<?> deleteById( @PathVariable Long id) throws Exception {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.deleteById(id));
        }




}



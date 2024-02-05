package com.antonio.sistema_stock.repositories;

import com.antonio.sistema_stock.dto.dtoRequest.ClientDtoRequest;
import com.antonio.sistema_stock.entities.Client;
import com.antonio.sistema_stock.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IClientRepository extends CrudRepository<Client, Long> {

    @Query("select new com.antonio.sistema_stock.dto.dtoRequest.ClientDtoRequest(c.cuit,c.name, c.email, c.lastname, c.direction, c.gross_income, c.enable ) from Client c where c.user= ?1 and c.enable=true")
    List<ClientDtoRequest> findAll(User user);


}

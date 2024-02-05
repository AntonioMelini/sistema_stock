package com.antonio.sistema_stock.services;

import com.antonio.sistema_stock.dto.dtoRequest.ClientDtoRequest;

import java.util.List;

public interface IClientService {
    String insert(ClientDtoRequest client, String username) throws Exception;
    List<ClientDtoRequest> getAll(String username) throws Exception;
    ClientDtoRequest getById(Long id) throws Exception;
    String updateById(Long id, ClientDtoRequest client) throws Exception;
    String deleteById(Long id) throws Exception;


}

package com.antonio.sistema_stock.services;

import com.antonio.sistema_stock.dto.dtoRequest.UserDtoRequest;
import com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse;

import java.util.List;

public interface IUserService {
    List<UserDtoResponse> getAll();
    List<UserDtoResponse> getAllInactive();
    UserDtoResponse insert(UserDtoRequest userDtoRequest) throws Exception;
    UserDtoResponse getByCuit(String cuit) ;
    UserDtoResponse getByBusinessName(String name);
    String deleteByCuit(String cuit);


}

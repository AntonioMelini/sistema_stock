package com.antonio.sistema_stock.services;

import com.antonio.sistema_stock.dto.dtoRequest.UserDtoRequest;
import com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse;
import jakarta.validation.ConstraintViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface IUserService {
    List<UserDtoResponse> getAll();
    List<UserDtoResponse> getAllInactive();


      UserDtoResponse registerAdmin(UserDtoRequest userDtoRequest) throws Exception;
      UserDtoResponse insert(UserDtoRequest userDtoRequest) ;
    UserDtoResponse getByCuit(String cuit)  ;

    UserDtoResponse getByBusinessName(String name);
    String disableByCuit(String cuit);
    String deleteByCuit(String cuit);


}

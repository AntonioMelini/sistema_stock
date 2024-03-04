package com.antonio.sistema_stock.services;

import com.antonio.sistema_stock.dto.dtoRequest.UserDtoRequest;
import com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse;
import com.antonio.sistema_stock.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {
    List<UserDtoResponse> getAll();
    List<UserDtoResponse> getAllInactive();


      UserDtoResponse registerAdmin(UserDtoRequest userDtoRequest) throws Exception;
      UserDtoResponse insert(UserDtoRequest userDtoRequest) ;
    UserDtoResponse getByCuit(String cuit)  ;

    UserDtoResponse getByBusinessName(String name);
    String disableByCuit(String cuit);
    String deleteByCuit(String cuit);

    Optional<User> findByUsername(String username);

    Map<String , String> getUserInfo(String jwt);

    void refreshToken(HttpServletRequest request,
                      HttpServletResponse response) throws IOException;


}

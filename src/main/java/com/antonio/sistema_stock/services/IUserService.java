package com.antonio.sistema_stock.services;

import com.antonio.sistema_stock.entities.User;
import com.antonio.sistema_stock.models.dto.UserDto;

import java.util.List;

public interface IUserService {
    List<UserDto> getAll();
    UserDto insert(UserDto userDto) throws Exception;
    UserDto getByCuit(String cuit);
    UserDto getByBusinessName(String name);
    String deleteByCuit(String cuit);


}

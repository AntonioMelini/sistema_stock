package com.antonio.sistema_stock.services;

import com.antonio.sistema_stock.models.dto.UserDto;

import java.util.List;

public interface IUserService {
   public List<UserDto> getAll();
     public String   insert(UserDto userDto);
}

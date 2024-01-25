package com.antonio.sistema_stock.services.impl;

import com.antonio.sistema_stock.entities.User;
import com.antonio.sistema_stock.models.dto.UserDto;
import com.antonio.sistema_stock.repositories.IUserRepository;
import com.antonio.sistema_stock.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public List<UserDto> getAll() {
        return mapUserToUserDto( (List<User>) userRepository.findAll());

    }

    @Override
    public String insert(UserDto userDto) {
        return null;
    }

    private List<UserDto> mapUserToUserDto(List<User> users){
        List<UserDto> usersDto=new ArrayList<>();
        for (User u:users) {
            UserDto userDto= new UserDto();
            userDto.setAge(u.getAge());
            userDto.setEmail(u.getEmail());
            userDto.setGender(u.getGender());
            userDto.setName(u.getName());
            userDto.setLastname(u.getLastname());
            usersDto.add(userDto);
        }
        return usersDto;

    }
}

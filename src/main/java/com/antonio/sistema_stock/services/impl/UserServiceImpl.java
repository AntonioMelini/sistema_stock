package com.antonio.sistema_stock.services.impl;

import com.antonio.sistema_stock.entities.User;
import com.antonio.sistema_stock.models.dto.UserDto;
import com.antonio.sistema_stock.repositories.IUserRepository;
import com.antonio.sistema_stock.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    ////////////////////////
    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAll() {
        return mapUserToUserDtos( (List<User>) userRepository.findAll());

    }

    //////////////////////////////////////////
    @Transactional(readOnly = true)
    @Override
    public UserDto getByCuit(String cuit) {
        try {
            return mapUserToUserDto(userRepository.findByCuit(cuit).orElseThrow());

        }catch (Exception e){
            System.out.println("se pudrio");
            return null;
        }
    }
    /////////////////////////////
    @Transactional(readOnly = true)
    @Override
    public UserDto getByBusinessName(String name) {
        try{
            return mapUserToUserDto(userRepository.findByBusinessName(name).orElseThrow());
        }catch (Exception e){
            System.out.println("se pudrio");
            return null;
        }
    }
    /////////////////////////////////////////
    @Transactional()
    @Override
    public String deleteByCuit(String cuit) {

        Optional<User> userOptional=userRepository.findByCuit(cuit);
        if (userOptional.isPresent()){
            User user= userOptional.orElseThrow();
            user.setActive(false);
            userRepository.save(user);
            return "SE MODIFICO CORRECTAMENTE";
        }
        return "NO SE MODIFICO UN HUIEVO";
    }

    ///////////////////////////////////////////////////////////////////////////


    @Transactional()
    @Override
    public UserDto insert(UserDto userDto) throws Exception {
        Boolean userCuit=userRepository.findByCuit(userDto.getCuit()).isEmpty();
        Boolean userBusinessN=userRepository.findByBusinessName(userDto.getBusiness_name()).isEmpty();
        Boolean userEmail=userRepository.findByEmail(userDto.getEmail()).isEmpty();
        Boolean userUsername=userRepository.findByUsername(userDto.getUsername()).isEmpty();
        if (userCuit && userBusinessN && userEmail && userUsername) {
            User user = mapUserDtoToUserInsert(userDto);
            return mapUserToUserDto(userRepository.save(user));
        }else if(!userCuit && !userBusinessN && !userEmail && !userUsername) throw new Exception("No se puede crear, ya hay un registro de este usuario");
        else if(!userUsername)throw new Exception("No se puede crear, ya hay un usuario con ese username registrado");
        else if (!userBusinessN)throw new Exception("No se puede crear, ya hay un registro con este business_name");
        else if (!userCuit)throw new Exception("No se puede crear, ya hay un registro con este CUIT");
            else throw new Exception("No se puede crear, ya hay un usuario con ese email registrado");



    }

    ///////////////////////////////////////////////////////

    private UserDto mapUserToUserDto(User u){
       UserDto userDto=new UserDto();
            userDto.setCuit(u.getCuit());
            userDto.setEmail(u.getEmail());
            userDto.setBusiness_direction(u.getBusiness_direction());
            userDto.setBusiness_name(u.getBusiness_name());
            userDto.setUsername(u.getUsername());
            userDto.setPassword(u.getPassword());
            userDto.setGross_income(u.getGross_income());
            userDto.setAdmin(u.getAdmin());
            userDto.setActive(u.getActive());
        return userDto;

    }
    private List<UserDto> mapUserToUserDtos(List<User> users){
        List<UserDto> usersDto=new ArrayList<>();
        for (User u:users) {
            UserDto userDto= new UserDto();
            userDto.setCuit(u.getCuit());
            userDto.setEmail(u.getEmail());
            userDto.setBusiness_direction(u.getBusiness_direction());
            userDto.setBusiness_name(u.getBusiness_name());
            userDto.setUsername(u.getUsername());
            userDto.setPassword(u.getPassword());
            userDto.setGross_income(u.getGross_income());
            userDto.setAdmin(u.getAdmin());
            userDto.setActive(u.getActive());
            usersDto.add(userDto);
        }
        return usersDto;

    }
    private User mapUserDtoToUserInsert(UserDto u){
        User user= new User();
        user.setCuit(u.getCuit());
        user.setEmail(u.getEmail());
        user.setBusiness_direction(u.getBusiness_direction());
        user.setBusiness_name(u.getBusiness_name());
        user.setUsername(u.getUsername());
        user.setPassword(u.getPassword());
        user.setGross_income(u.getGross_income());

        if(u.getAdmin() == null || u.getAdmin() == false){
            user.setAdmin(false);
        }else{
            user.setAdmin(true);
        }

        if(u.getActive() == null || u.getActive() == true){
            user.setActive(true);
        }else{
            user.setActive(false);
        }

        return user;

    }

}

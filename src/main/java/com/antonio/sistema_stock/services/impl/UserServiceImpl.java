package com.antonio.sistema_stock.services.impl;

import com.antonio.sistema_stock.entities.User;
import com.antonio.sistema_stock.dto.dtoRequest.UserDtoRequest;
import com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse;
import com.antonio.sistema_stock.repositories.IUserRepository;
import com.antonio.sistema_stock.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public List<UserDtoResponse> getAll() {
        //return mapUserToUserDtosResponse( (List<User>) userRepository.findAll(),"active");

        //userRepository.findAllByOrderByUsernameAsc().stream().forEach(u-> System.out.println(u.getUsername()));
        return userRepository.findAllUsers();


    }
    @Transactional(readOnly = true)
    @Override
    public List<UserDtoResponse> getAllInactive() {
        //return mapUserToUserDtosResponse( (List<User>) userRepository.findAll(),"inactive");
        return userRepository.findAllUsersInactive();
    }

    //////////////////////////////////////////
    @Transactional(readOnly = true)
    @Override
    public UserDtoResponse getByCuit(String cuit) {

           // return mapUserToUserDtoResponse(userRepository.findByCuit(cuit).orElseThrow(()-> new Exception("No se encontro")));
            return userRepository.findByCuit(cuit);

    }
    /////////////////////////////
    @Transactional(readOnly = true)
    @Override
    public UserDtoResponse getByBusinessName(String name) {
        try{
            return mapUserToUserDtoResponse(userRepository.findByBusinessName(name).orElseThrow());
        }catch (Exception e){
            System.out.println("se pudrio");
            return null;
        }
    }
    /////////////////////////////////////////

    @Transactional()
    @Override
    public String deleteByCuit(String cuit) {

        Optional<User> userOptional=userRepository.findUserByCuit(cuit);
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
    public UserDtoResponse insert(UserDtoRequest userDtoRequest) throws Exception {
        Boolean userCuit=userRepository.findUserByCuit(userDtoRequest.getCuit()).isEmpty();
        Boolean userBusinessN=userRepository.findByBusinessName(userDtoRequest.getBusiness_name()).isEmpty();
        Boolean userEmail=userRepository.findByEmail(userDtoRequest.getEmail()).isEmpty();
        Boolean userUsername=userRepository.findByUsername(userDtoRequest.getUsername()).isEmpty();
        if (userCuit && userBusinessN && userEmail && userUsername) {
            User user = mapUserDtoRequestToUserInsert(userDtoRequest);
            return mapUserToUserDtoResponse(userRepository.save(user));
        }else if(!userCuit && !userBusinessN && !userEmail && !userUsername) throw new Exception("No se puede crear, ya hay un registro de este usuario");
        else if(!userUsername)throw new Exception("No se puede crear, ya hay un usuario con ese username registrado");
        else if (!userBusinessN)throw new Exception("No se puede crear, ya hay un registro con este business_name");
        else if (!userCuit)throw new Exception("No se puede crear, ya hay un registro con este CUIT");
            else throw new Exception("No se puede crear, ya hay un usuario con ese email registrado");



    }



    ///////////////////////////////////////////////////////

    private UserDtoResponse mapUserToUserDtoResponse(User u){
       UserDtoResponse userDtoRequest =new UserDtoResponse();
            userDtoRequest.setCuit(u.getCuit());
            userDtoRequest.setEmail(u.getEmail());
            userDtoRequest.setBusiness_direction(u.getBusiness_direction());
            userDtoRequest.setBusiness_name(u.getBusiness_name());
            userDtoRequest.setUsername(u.getUsername());
            userDtoRequest.setGross_income(u.getGross_income());

        return userDtoRequest;

    }

    private User mapUserDtoRequestToUserInsert(UserDtoRequest u){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode(u.getPassword());
        User user= new User();
        user.setCuit(u.getCuit());
        user.setEmail(u.getEmail());
        user.setBusiness_direction(u.getBusiness_direction());
        user.setBusiness_name(u.getBusiness_name());
        user.setUsername(u.getUsername());
        user.setPassword(result);
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

package com.antonio.sistema_stock.services.impl;


import com.antonio.sistema_stock.entities.User;
import com.antonio.sistema_stock.dto.dtoRequest.UserDtoRequest;
import com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse;
import com.antonio.sistema_stock.exceptions.user.UserCreateValidation;
import com.antonio.sistema_stock.exceptions.user.UserNotFound;
import com.antonio.sistema_stock.repositories.IUserRepository;
import com.antonio.sistema_stock.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
        try {
            // return mapUserToUserDtoResponse(userRepository.findByBusinessName(name).orElseThrow());
            return null;
        } catch (Exception e) {
            System.out.println("se pudrio");
            return null;
        }
    }
    /////////////////////////////////////////

    @Transactional()
    @Override
    public String deleteByCuit(String cuit) {

        Optional<User> userOptional = userRepository.findUserByCuit(cuit);
        if (userOptional.isPresent()) {
            User user = userOptional.orElseThrow();
            user.setActive(false);
            userRepository.save(user);
            return "SE MODIFICO CORRECTAMENTE";
        }
        throw new UserNotFound("User not Found");
    }



    ///////////////////////////////////////////////////////////////////////////


    @Transactional()
    @Override
    public UserDtoResponse insert(UserDtoRequest userDtoRequest)  {
        Boolean userCuit=userRepository.findUserByCuit(userDtoRequest.getCuit()).isEmpty();
        Boolean userBusinessN=userRepository.findByBusinessName(userDtoRequest.getBusiness_name()).isEmpty();
        Boolean userEmail=userRepository.findByEmail(userDtoRequest.getEmail()).isEmpty();
        Boolean userUsername=userRepository.findByUsername(userDtoRequest.getUsername()).isEmpty();
        Boolean userGroosIncome = userRepository.findUserByGrossIncome(userDtoRequest.getGross_income()).isEmpty();
        if (userCuit && userBusinessN && userEmail && userUsername) {
            User user = mapUserDtoRequestToUserInsert(userDtoRequest,"ROLE_USER");
            return mapUserToUserDtoResponse(userRepository.save(user));
        }else if(!userCuit && !userBusinessN && !userEmail && !userUsername && !userGroosIncome) throw new UserCreateValidation("No se puede crear, ya hay un registro de este usuario");
        else if(!userUsername)throw new UserCreateValidation("No se puede crear, ya hay un usuario con ese username registrado");
        else if (!userBusinessN)throw new UserCreateValidation("No se puede crear, ya hay un registro con este business_name");
        else if (!userGroosIncome)throw new UserCreateValidation("No se puede crear, ya hay un registro con este userGroosIncome");
        else if (!userCuit)throw new UserCreateValidation("No se puede crear, ya hay un registro con este CUIT");
        else throw new UserCreateValidation("No se puede crear, ya hay un usuario con ese email registrado");

    }



    @Transactional()
    @Override
    public UserDtoResponse registerAdmin(UserDtoRequest userDtoRequest) throws Exception {
        Boolean userCuit=userRepository.findUserByCuit(userDtoRequest.getCuit()).isEmpty();
        Boolean userBusinessN=userRepository.findByBusinessName(userDtoRequest.getBusiness_name()).isEmpty();
        Boolean userEmail=userRepository.findByEmail(userDtoRequest.getEmail()).isEmpty();
        Boolean userUsername=userRepository.findByUsername(userDtoRequest.getUsername()).isEmpty();
        if (userCuit && userBusinessN && userEmail && userUsername) {
            User user = mapUserDtoRequestToUserInsert(userDtoRequest,"ROLE_ADMIN");
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

    private User mapUserDtoRequestToUserInsert(UserDtoRequest u,String role){
        if(u.getPassword().isBlank()) throw new UserCreateValidation("Inserte una contraseña valida");
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
        user.setRole(role);
        user.setActive(u.getActive() == null || u.getActive());

        return user;

    }

}



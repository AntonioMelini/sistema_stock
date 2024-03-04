package com.antonio.sistema_stock.services.impl;


import com.antonio.sistema_stock.entities.User;
import com.antonio.sistema_stock.dto.dtoRequest.UserDtoRequest;
import com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse;
import com.antonio.sistema_stock.exceptions.auth.InvalidToken;
import com.antonio.sistema_stock.exceptions.user.UserCreateValidation;
import com.antonio.sistema_stock.exceptions.user.UserNotFound;
import com.antonio.sistema_stock.repositories.IProductRepository;
import com.antonio.sistema_stock.repositories.IUserRepository;
import com.antonio.sistema_stock.security.jwt.JwtUtils;
import com.antonio.sistema_stock.services.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

import static com.antonio.sistema_stock.security.jwt.JwtUtils.CONTENT_TYPE;
import static com.antonio.sistema_stock.security.jwt.JwtUtils.SECRET_KEY;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private JwtUtils jwtUtils;

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
    public String disableByCuit(String cuit) {

        Optional<User> userOptional = userRepository.findUserByCuit(cuit);
        if (userOptional.isPresent()) {
            User user = userOptional.orElseThrow();
            user.setActive(false);
            userRepository.save(user);
            return "SE MODIFICO CORRECTAMENTE";
        }
        throw new UserNotFound("User not Found");
    }
    @Transactional()
    @Override
    public String deleteByCuit(String cuit) {
        System.out.println("entro a delete service");
        Optional<User> userOptional = userRepository.findUserByCuit(cuit);
        if (userOptional.isPresent()) {
            productRepository.deleteAllProductsById(userOptional.get());
            userRepository.deleteById(cuit);
            return "Se elimino correctamente";
        }
        throw new UserNotFound("no se encontro el usuario");

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Map<String, String> getUserInfo(String jwt) {

            if(jwt.isBlank() ) throw new InvalidToken("ERROR EN EL TOKEN");
            System.out.println(jwt);
            String username = jwtUtils.getUsernameFromToken(jwt);
            User user= findByUsername(username).orElseThrow(()-> new UserNotFound("User not found"));
            Map <String , String> response = new HashMap<>();
            response.put("username",username);
            response.put("cuit",user.getCuit());
            response.put("business_direction",user.getBusiness_direction());
            response.put("business_name",user.getBusiness_name());
            response.put("email",user.getEmail());
            response.put("gross_income",user.getGross_income());
            response.put("roles",user.getRole());
            return response;
    }

    @Override
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        try {
            System.out.println(request.getHeader("Authorization"));
            String refresh_token_Header = request.getHeader("Authorization");
            if (refresh_token_Header != null || refresh_token_Header.startsWith("Bearer ")) {
                String refersh_token_old = refresh_token_Header.replace("Bearer ", "");
                if (jwtUtils.isTokenValid(refersh_token_old)) {
                    String username = jwtUtils.getUsernameFromToken(refersh_token_old);
                    User user=findByUsername(username).orElseThrow();
                    Long accessTokenExp = 1200000L;
                    // String[] roles = userService.findByUsername(username).orElseThrow(Exception::new).getRole().concat(",").split(",");
                    // System.out.println("ESto son los roles: "+ Arrays.stream(roles).toList());




                    String acces_token = Jwts.builder()
                            .subject(user.getUsername())
                            .expiration(new Date(System.currentTimeMillis() +accessTokenExp ))
                            .issuedAt(new Date(System.currentTimeMillis()))
                            .claim("roles", Collections.singletonList(user.getRole()))
                            .signWith(SECRET_KEY)
                            .compact();
                    String refresh_token = Jwts.builder()
                            .subject(user.getUsername())
                            .expiration(new Date(System.currentTimeMillis() +3600000L ))
                            .issuedAt(new Date(System.currentTimeMillis()))
                            .signWith(SECRET_KEY)
                            .compact();

                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", acces_token);
                    tokens.put("refresh_token", refresh_token);
                    response.setStatus(200);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),tokens);

                }else{
                    throw new InvalidToken("invalid token");
                }
            }else{
                throw new InvalidToken("invalid header athorization");
            }

        } catch (Exception e) {
            System.out.println("entro a unsuccessfulAuthentication");
            Map<String, String> body = new HashMap<>();
            body.put("Message", "Error en la autenticacion username o password incorrecto!");
            body.put("error", e.getMessage());

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(401);
            response.setContentType(CONTENT_TYPE);


        }
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



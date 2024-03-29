package com.antonio.sistema_stock.repositories;

import com.antonio.sistema_stock.entities.User;
import com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<User,String>  {

   //List<User> findAllByOrderByUsernameAsc();

    @Query("select new com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse(u.cuit, u.email, u.username, u.business_direction, u.business_name, u.gross_income) from User u where u.active=true and u.role='ROLE_USER'")
    List<UserDtoResponse> findAllUsers();

    @Query("select new com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse(u.cuit, u.email, u.username, u.business_direction, u.business_name, u.gross_income) from User u where u.active=false")
    List<UserDtoResponse> findAllUsersInactive();

    @Query("select new com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse(u.cuit, u.email, u.username, u.business_direction, u.business_name, u.gross_income) from User u where u.cuit=?1")
    UserDtoResponse findByCuit(String cuit);

    Optional<User> findUserByCuit (String cuit);

    @Override
    void deleteById(String cuit);

    @Query("select p from User p where p.gross_income= ?1")
    Optional<User> findUserByGrossIncome(String gross_income);

    @Query("select p from User p where p.business_name= ?1")
    Optional<User> findByBusinessName(String business_name);
   // @Query("select p from User p where p.username= ?1")
    Optional<User> findByUsername(String username);
    @Query("select p from User p where p.email= ?1")
    Optional<User> findByEmail(String email);



}

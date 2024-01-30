package com.antonio.sistema_stock.repositories;

import com.antonio.sistema_stock.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface IUserRepository extends CrudRepository<User,Long> {

    Optional<User> findByCuit(String cuit);
    @Query("select p from User p where p.business_name= ?1")
    Optional<User> findByBusinessName(String business_name);
    @Query("select p from User p where p.username= ?1")
    Optional<User> findByUsername(String username);
    @Query("select p from User p where p.email= ?1")
    Optional<User> findByEmail(String email);
    /*
    @Modifying
    @Query("update User u set u.active = false from u where u.cuit= ?1")
    void deleteByCuit(String cuit);

     */
}

package com.antonio.sistema_stock.repositories;

import com.antonio.sistema_stock.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface IUserRepository extends CrudRepository<User,Long> {
}

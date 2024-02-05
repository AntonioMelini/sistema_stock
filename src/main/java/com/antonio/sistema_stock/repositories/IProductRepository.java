package com.antonio.sistema_stock.repositories;

import com.antonio.sistema_stock.dto.dtoRequest.ProductDtoRequest;
import com.antonio.sistema_stock.entities.Product;
import com.antonio.sistema_stock.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository  extends CrudRepository<Product, Long> {




    //@Query("select new com.antonio.sistema_stock.dto.dtoResponse.UserDtoResponse(u.cuit, u.email, u.username, u.business_direction, u.business_name, u.gross_income) from User u where u.active=true and u.role='ROLE_USER'")
    @Query("Select new com.antonio.sistema_stock.dto.dtoRequest.ProductDtoRequest(p.name, p.price, p.stock, p.image, p.description , p.enable) from Product p where p.user=?1 and p.enable=true ")
    List<ProductDtoRequest> findAll(User user);
    @Query("Select new com.antonio.sistema_stock.dto.dtoRequest.ProductDtoRequest(p.name, p.price, p.stock, p.image, p.description ,p.enable) from Product p where p.id=?1")
    ProductDtoRequest findById(String id);

    @Modifying
    @Query("Delete from Product  p where p.user=?1")
    void deleteAllProductsById(User user);


    void deleteById(Long id);

}
/*
   @Query("UPDATE Product p SET p.name = ?2, c.price = ?3 p.stock= ?4  p.image= ?5 p.description= ?6 p.enable= ?7  WHERE p.id = ?1")
    int updateById(String id, String name, Double price, Long stock ,String image, String description, Boolean enable);
 */
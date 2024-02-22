package com.antonio.sistema_stock.services.impl;

import com.antonio.sistema_stock.dto.dtoRequest.ProductDtoRequest;
import com.antonio.sistema_stock.dto.dtoResponse.ProductDtoResponse;
import com.antonio.sistema_stock.entities.Product;
import com.antonio.sistema_stock.entities.User;
import com.antonio.sistema_stock.exceptions.user.UserNotFound;
import com.antonio.sistema_stock.repositories.IProductRepository;
import com.antonio.sistema_stock.repositories.IUserRepository;
import com.antonio.sistema_stock.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IUserRepository userRepository;

    @Transactional
    @Override
    public String insert(ProductDtoRequest product, String username) throws Exception {
        Product product1 =  productRepository.save(mapProductDtoToProduct(product,username));
        if (Objects.isNull(product1)) throw new Exception("error al agregar el producto");
        return "se agrego perfectamente";
    }
    @Transactional(readOnly = true)
    @Override
    public List<ProductDtoResponse> getAll(String username) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new Exception("usuario no registrado");
        return productRepository.findAll(user.get());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDtoRequest getById(String id) {
        return productRepository.findById(id);
    }


    @Modifying
    @Transactional()
    @Override
    public String updateById(Long id, ProductDtoRequest productDtoRequest) throws Exception {
       Optional<Product> productOptional = productRepository.findById(id);
       if (productOptional.isEmpty())throw new Exception("no se encontro el producto");
       Product product=productOptional.get();
       product.setPrice(!productDtoRequest.getPrice().toString().isEmpty() ? productDtoRequest.getPrice() : product.getPrice());
        product.setName(!productDtoRequest.getName().isEmpty() ? productDtoRequest.getName() : product.getName());
        product.setEnable(!productDtoRequest.isEnable().toString().isEmpty() ? productDtoRequest.isEnable() : product.isEnable());
        product.setImage(!productDtoRequest.getImage().isEmpty() ? productDtoRequest.getImage() : product.getImage());
        product.setDescription(!productDtoRequest.getDescription().isEmpty() ? productDtoRequest.getDescription() : product.getDescription());
        product.setStock(!productDtoRequest.getStock().toString().isEmpty() ? productDtoRequest.getStock() : product.getStock());
             productRepository.save(product);
        return "Se modifico correctamente";
    }

    @Transactional
    @Override
    public String deleteById(Long id) throws Exception {
        productRepository.deleteById(id);
       Optional<Product> product= productRepository.findById(id);
        if (product.isEmpty()) return "se elimino perfectamente";
        throw new Exception("no se elimino");
    }

    @Override
    public String subtractStockById(Long id, Long stock) throws Exception {
       Product product =  productRepository.findById(id).orElseThrow(Exception::new);

       if (product.getStock() - stock < 0) throw new Exception("ACA HACER EXCEPCION DE : Stock invalid :");
       product.setStock(product.getStock()-stock);
       productRepository.save(product);
       return "se modifico perfectamente el stock";
    }




    public String addStockById(Long id, Long stock) throws Exception {
        Product product =  productRepository.findById(id).orElseThrow(Exception::new);
        product.setStock(product.getStock()+stock);
        productRepository.save(product);
        return "se agrego perfectamente stock";
    }







    private Product mapProductDtoToProduct(ProductDtoRequest productDtoRequest, String username){
        Optional<User> user= userRepository.findByUsername(username);
        if (user.isEmpty()) throw new UserNotFound("User not Found");
        Product product= new Product();
        product.setDescription(productDtoRequest.getDescription());
        product.setImage(productDtoRequest.getImage());
        product.setName(productDtoRequest.getName());
        product.setStock(productDtoRequest.getStock());
        product.setUser(user.get());
        product.setPrice(productDtoRequest.getPrice());
        product.setEnable(productDtoRequest.isEnable());
        return product;
    }
}

package com.antonio.sistema_stock.services;

import com.antonio.sistema_stock.dto.dtoRequest.ProductDtoRequest;

import java.util.List;

public interface IProductService {
    String insert(ProductDtoRequest product,String username) throws Exception;
    List<ProductDtoRequest> getAll(String username) throws Exception;
    ProductDtoRequest getById(String id);
    String updateById(Long id,ProductDtoRequest productDtoRequest) throws Exception;
    String deleteById(Long id) throws Exception;


}

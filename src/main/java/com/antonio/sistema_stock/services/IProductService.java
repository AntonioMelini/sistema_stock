package com.antonio.sistema_stock.services;

import com.antonio.sistema_stock.dto.dtoRequest.ProductDtoRequest;
import com.antonio.sistema_stock.dto.dtoResponse.ProductDtoResponse;

import java.util.List;

public interface IProductService {
    String insert(ProductDtoRequest product, String username) throws Exception;
    List<ProductDtoResponse> getAll(String username) throws Exception;
    ProductDtoRequest getById(String id);
    String updateById(Long id, ProductDtoRequest productDtoRequest) throws Exception;
    String deleteById(Long id) throws Exception;
    String subtractStockById(Long id,Long stock) throws Exception;
    String addStockById(Long id, Long stock) throws Exception;


}

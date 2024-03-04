package com.antonio.sistema_stock.exceptions.product;

public class StockInvalid extends RuntimeException{
    public StockInvalid (String msg){
        super(msg);
    }
}

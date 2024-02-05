package com.antonio.sistema_stock.exceptions.user;

public class UserCreateValidation extends RuntimeException{
    public UserCreateValidation(String msg){
        super(msg);
    }
}

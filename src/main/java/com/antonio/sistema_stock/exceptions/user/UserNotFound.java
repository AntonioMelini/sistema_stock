package com.antonio.sistema_stock.exceptions.user;

public class UserNotFound extends RuntimeException{
    public UserNotFound (String msg){
        super(msg);
    }
}

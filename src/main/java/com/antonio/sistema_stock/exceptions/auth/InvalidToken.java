package com.antonio.sistema_stock.exceptions.auth;

public class InvalidToken extends RuntimeException{
    public InvalidToken (String msg){super(msg);}
}

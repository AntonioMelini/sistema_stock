package com.antonio.sistema_stock.dto.dtoResponse;

public class UserDtoResponse {
    private String cuit;
    private String email;
    private String username;
    private  String business_direction;
    private String business_name;
    private  String gross_income;

    public UserDtoResponse() {
    }

    public UserDtoResponse(String cuit, String email, String username, String business_direction, String business_name, String gross_income) {
        this.cuit = cuit;
        this.email = email;
        this.username = username;
        this.business_direction = business_direction;
        this.business_name = business_name;
        this.gross_income = gross_income;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBusiness_direction() {
        return business_direction;
    }

    public void setBusiness_direction(String business_direction) {
        this.business_direction = business_direction;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getGross_income() {
        return gross_income;
    }

    public void setGross_income(String gross_income) {
        this.gross_income = gross_income;
    }
}

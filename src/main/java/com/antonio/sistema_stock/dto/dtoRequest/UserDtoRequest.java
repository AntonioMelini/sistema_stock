package com.antonio.sistema_stock.dto.dtoRequest;

public class UserDtoRequest {

    private String cuit;
    private String email;
    private String username;
    private String password;
    private  String business_direction;
    private String business_name;
    private  String gross_income;
    private Boolean admin;
    private Boolean active;

    public Boolean getActive() {
        System.out.println("esto es el setActive"+active);
        return active;
    }

    public void setActive(Boolean active) {
        System.out.println("esto es el setActive"+active);
        if(active==null || active==true){
            this.active= true;
        }else{
            this.active=false;
        }
        System.out.println("esto es el setActive despues "+this.active);
    }

    public Boolean getAdmin() {
        System.out.println("esto es el getAdmin"+admin);
        return admin;

    }

    public void setAdmin(Boolean admin) {
        System.out.println("esto es el setAdmin"+admin);
        if(admin==null || admin==false){
            this.admin= false;
        }else{
            this.admin=true;
        }
        System.out.println("esto es el setAdmin despues "+this.admin);
    }

    public UserDtoRequest() {
    }

    public UserDtoRequest(String cuit, String email, String username, String password, String business_direction, String business_name, String gross_income) {
        this.cuit = cuit;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return
                "cuit='" + cuit + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", business_direction='" + business_direction + '\'' +
                ", business_name='" + business_name + '\'' +
                ", gross_income='" + gross_income + '\'' +
                ", admin=" + admin +
                '}';
    }
}

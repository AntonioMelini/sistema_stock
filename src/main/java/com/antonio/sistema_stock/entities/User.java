package com.antonio.sistema_stock.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;


@Entity
@Table(name = "users")
public class User {
    @Id
    @Size(min = 11,max = 11,message = "Insert a correct CUIT")
    @NotBlank
    private String cuit;
    @Column(name = "email",nullable = false,unique = true)
    @Email(message = "Insert a valid email")
    @NotBlank
    private String email;
    @Column(name = "username",nullable = false,unique = true)
    @NotBlank
    private String username;
    @Column(name = "password",nullable = false)
    @NotBlank
    private String password;
    @Column(name = "business_direction",nullable = false)
    @NotBlank
    private  String business_direction;
    @Column(name = "business_name",nullable = false)
    @NotBlank
    private String business_name;
    @Column(name = "gross_income",nullable = false)
    @Size(min = 14,max = 14,message = "Insert a correct gross_income")
    @NotBlank
    private  String gross_income;
    @Column(columnDefinition = "boolean default false")
    private Boolean admin;
    @Column(columnDefinition = "boolean default true")
    private Boolean active;

    @Embedded
    private Audit audit=new Audit();





    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        if(active==null || active==false){
            this.active= false;
        }else{
            this.active=true;
        }
    }

    public String getCuit() {
        return cuit;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        if(admin==null || admin==false){
            this.admin= false;
        }else{
            this.admin=true;
        }

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
        return "User{" +
                "cuit='" + cuit + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", business_direction='" + business_direction + '\'' +
                ", business_name='" + business_name + '\'' +
                ", gross_income='" + gross_income + '\'' +
                ", admin=" + admin +
                ", active=" + active +
                '}';
    }


}

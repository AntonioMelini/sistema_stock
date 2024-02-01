package com.antonio.sistema_stock.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Size(min = 11,max = 11,message = "Insert a correct CUIT")
    @NotBlank(message = "Insert a correct CUIT")
    private String cuit;

    @Column(name = "email",nullable = false,unique = true)
    @Email(message = "Insert a valid email")
    @NotBlank(message = "Insert a valid email")
    private String email;

    @Column(name = "username",nullable = false,unique = true)
    @NotBlank(message = "Insert a correct username")
    private String username;

    @Column(name = "password",nullable = false)
    @NotBlank(message = "Insert a correct password")
    @Size(min = 5,message = "invalid password, must be more than 5 charactersmfor be a valid passowrd")
    private String password;

    @Column(name = "business_direction",nullable = false)
    @NotBlank(message = "Insert a correct business_direction")
    private  String business_direction;
    @Column(name = "business_name",nullable = false)
    @NotBlank(message = "Insert a correct business_name")
    private String business_name;
    @Column(name = "gross_income",nullable = false)
    @Size(min = 14,max = 14,message = "Insert a correct gross_income")
    @NotBlank(message = "Insert a correct gross_income")
    private  String gross_income;
    @Column(columnDefinition = "boolean default false")
    private Boolean active;

   /* @ManyToOne(targetEntity = Role.class)
    private Role roles;
    */
    @Column(name = "role",nullable = false)
    @Size(min = 9,max = 10,message = "Insert a correct role")
    @NotBlank(message = "Insert a correct role")
    private String role;
    @Transient
    private Boolean admin;

    @Embedded
    private Audit audit=new Audit();





    public Boolean getActive() {
        return active;
    }

    public User() {
    }

    public User(String cuit, String email, String username, String password, String business_direction, String business_name, String gross_income, Boolean active, String role, Boolean admin) {
        this.cuit = cuit;
        this.email = email;
        this.username = username;
        this.password = password;
        this.business_direction = business_direction;
        this.business_name = business_name;
        this.gross_income = gross_income;
        this.active = active;
        this.role = role;
        this.admin = admin;
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


    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin=  admin ? admin : false;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
                ", active=" + active +
                '}';
    }


}

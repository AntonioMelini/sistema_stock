package com.antonio.sistema_stock.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @NotNull(message = "cuit cant be null")
    private Long cuit;
    @NotBlank(message = "name cant be blank")
    private String name;
    @NotBlank(message = "email cant be null")
    @Email(message = "send a valid email")
    @Column(unique = true,nullable = false)
    private String email;
    @NotBlank(message = "lastname cant be blank")
    private String lastname;
    @NotBlank(message = "direction cant be blank")
    private String direction;
    @NotBlank(message = "gross_income cant be blank")
    @Size(min = 11,max = 11, message = "gross_income cant be blank" )
    private String gross_income;
    @Column(columnDefinition = "boolean default true")
    private Boolean enable;

    @ManyToOne
    private User user;

    @Embedded
    private final Audit audit=new Audit();

    public Client() {
    }

    public Client(Long cuit, String name,String email, String lastname, String direction, String gross_income, Boolean enable, User user) {
        this.cuit = cuit;
        this.name = name;
        this.email= email;
        this.lastname = lastname;
        this.direction = direction;
        this.gross_income = gross_income;
        if(enable == null || enable)this.enable=true;
        else this.enable=false;
        this.user = user;
    }

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        if(enable == null || enable) this.enable=true;
        else this.enable=false;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getGross_income() {
        return gross_income;
    }

    public void setGross_income(String gross_income) {
        this.gross_income = gross_income;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Client{" +
                "cuit=" + cuit +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastname='" + lastname + '\'' +
                ", direction='" + direction + '\'' +
                ", gross_income='" + gross_income + '\'' +
                ", enable=" + enable +
                ", user=" + user +
                ", audit=" + audit +
                '}';
    }
}

package com.antonio.sistema_stock.dto.dtoRequest;

import com.antonio.sistema_stock.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClientDtoRequest {
   private Long cuit;
   private String name;
   private String email;
   private String lastname;
   private String direction;
   private String gross_income;
   private Boolean enable;

    public ClientDtoRequest() {
    }

    public ClientDtoRequest(Long cuit, String name,String email, String lastname, String direction, String gross_income, Boolean enable) {
        this.cuit = cuit;
        this.name = name;
        this.email= email;
        this.lastname = lastname;
        this.direction = direction;
        this.gross_income = gross_income;
        this.enable = enable;
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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "ClientDtoRequest{" +
                "cuit=" + cuit +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastname='" + lastname + '\'' +
                ", direction='" + direction + '\'' +
                ", gross_income='" + gross_income + '\'' +
                ", enable=" + enable +
                '}';
    }
}

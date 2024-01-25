package com.antonio.sistema_stock.models.dto;

public class UserDto {
    private String name;
    private  String lastname;
    private String gender;
    private String email;
    private Integer age;

    public UserDto(String name, String lastname, String gender, String email, Integer age) {
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
        this.age = age;
    }

    public UserDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

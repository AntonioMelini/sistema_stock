package com.antonio.sistema_stock.dto.dtoRequest;

public class ProductDtoRequest {

    private String name;

    private Double price ;

    private Long stock;
    private String image;
    private String description;
    private Boolean enable;

    public ProductDtoRequest() {
    }

    public ProductDtoRequest(String name, Double price, Long stock, String image, String description, Boolean enable) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.description = description;
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}

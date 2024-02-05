package com.antonio.sistema_stock.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "insert a correct name")
    private String name;
    @Column(nullable = false)
    @NotNull(message = "insert a correct price")
    private Double price ;
    @Column(nullable = false)
    @NotNull(message = "insert a correct name")
    private Long stock;
    private String image;
    @Column(nullable = false)
    @NotNull(message = "insert a correct product description")
    private String description;
    @Column(columnDefinition = "boolean default true")
    private Boolean enable;

    @ManyToOne
    private User user;

    @Embedded
    private final Audit audit=new Audit();

    public Product() {
    }

    public Product(Long id, String name, Double price, Long stock, String image, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.enable = enable ? enable :false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.javaee.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(indexes = @Index(name = "product_name_index", columnList = "name", unique = true))
@NamedQueries({
    @NamedQuery(name = "findProductsByName", query = "SELECT p FROM Product p WHERE p.name LIKE :name"),
    @NamedQuery(name = "findNotAvailableProductCount", query = "SELECT COUNT(p) FROM Product p WHERE p.available = FALSE")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "is_available", nullable = false)
    private boolean available;

    public long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
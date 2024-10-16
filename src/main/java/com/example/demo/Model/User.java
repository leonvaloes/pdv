package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "name", unique = true)
    private String password;

    public User() {
    }

    public User(int id, int quantity, String password) {
        this.id = id;
        this.quantity = quantity;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

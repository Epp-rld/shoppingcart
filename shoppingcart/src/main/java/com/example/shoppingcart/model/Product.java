package com.example.shoppingcart.model;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
@ToString

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;

    // Calculate total price based on price and quantity. Return total price as double.
    public double totalPrice() {
        if (price == 0 || quantity == 0) {
            throw new IllegalArgumentException("Price and quantity are required.");
        }
        return price * quantity;
    }
}
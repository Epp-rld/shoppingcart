package com.example.shoppingcart.service;

import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartService {

    private final ProductRepository productRepository;

    @Autowired
    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Add a new product to the cart
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Get all items in the cart
    public List<Product> getCartItems() {
        return productRepository.findAll();
    }

    // Remove a product from the cart by name
    public void removeProductByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        Product product = productRepository.findByName(name);
        if (product != null) {
            productRepository.delete(product);
        } else {
            throw new IllegalArgumentException("Product " + name + " not found.");
        }
    }

    // Calculate the total price of all products in the cart
    @Scheduled(fixedRate = 10000)
    public double calculateCartTotal() {
        List<Product> cartItems = productRepository.findAll();
        double total = 0;
        for (Product product : cartItems) {
            total += product.totalPrice();
        }
        System.out.println("Scheduler total: " + (total * calculateTax()));
        return total;
    }

    // Calculate 22% tax on the total cart value
    public double calculateTax() {
        double total = calculateCartTotal();
        double tax = total * 0.22; // 22% tax rate
        return tax;
    }

    // Apply 10% discount for members
    public double applyMemberCardDiscount(boolean isMember) {
        double total = calculateCartTotal();

        if (isMember) {
            double discount = total * 0.10; // 10% discount
            total -= discount; // Apply discount
        }
        return total;
    }
}

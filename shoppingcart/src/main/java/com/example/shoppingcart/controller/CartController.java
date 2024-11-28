package com.example.shoppingcart.controller;

import com.example.shoppingcart.service.CartService;
import com.example.shoppingcart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add a new product to the cart
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Product savedProduct = cartService.addProduct(product); // Add the product using the service
            return ResponseEntity.ok(savedProduct); // Return the saved product with a 200 OK status
        } catch (IllegalArgumentException e) {
            // If there is an error, return 400 Bad Request with error message
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get all items in the cart
    @GetMapping("/items")
    public ResponseEntity<List<Product>> getCartItems() {
        List<Product> products = cartService.getCartItems();
        return ResponseEntity.ok(products); // Return list of products with 200 OK status
    }

    // Remove a product from the cart by name
    @DeleteMapping("/remove/{name}")
    public ResponseEntity<String> removeProduct(@PathVariable String name) {
        try {
            cartService.removeProductByName(name); // Call service to remove product by name
            return ResponseEntity.ok("Product removed successfully.");
        } catch (IllegalArgumentException e) {
            // Return an error message if product with the given name is not found
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Calculate the total cost of all products in the cart
    @GetMapping("/total")
    public ResponseEntity<Double> calculateCartTotal() {
        double total = cartService.calculateCartTotal(); // Call service to calculate total cost
        return ResponseEntity.ok(total); // Return the total cost with 200 OK status
    }

    // Calculate the tax on the total cart amount (22%)
    @GetMapping("/tax")
    public ResponseEntity<Double> calculateTax() {
        double tax = cartService.calculateTax(); // Call service method to calculate tax
        return ResponseEntity.ok(tax); // Return calculated tax as response
    }
}

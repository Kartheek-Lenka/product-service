package com.example.product.service;

import com.example.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public List<Product> getProducts() {
        return List.of(
                new Product(1, "Laptop"),
                new Product(2, "Phone"),
                new Product(3, "Headphones")
        );
    }
}
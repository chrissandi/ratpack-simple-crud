package com.example.product.service;

import com.example.product.model.Product;
import ratpack.exec.Promise;

import java.util.List;

public interface ProductService {
    Promise<List<Product>> getAllProducts();

    Promise<Product> getProductById(Long id);

    Promise<Product> createProduct(Product product);

    Promise<Product> updateProduct(Long id, Product product);

    Promise<Void> deleteProduct(Long id);
}
package com.example.product.service.impl;

import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import com.google.inject.Inject;
import ratpack.exec.Promise;
import ratpack.exec.Blocking;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Inject
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Promise<List<Product>> getAllProducts() {
        return Blocking.get(() -> productRepository.getAllProducts());
    }

    @Override
    public Promise<Product> getProductById(Long id) {
        return Blocking.get(() -> productRepository.getProductById(id));
    }

    @Override
    public Promise<Product> createProduct(Product product) {
        return Blocking.get(() -> {
            productRepository.saveProduct(product);
            return product;
        });
    }

    @Override
    public Promise<Product> updateProduct(Long id, Product updatedProduct) {
        return Blocking.get(() -> {
            Product existingProduct = productRepository.getProductById(id);

            if (existingProduct != null) {
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setPrice(updatedProduct.getPrice());
                productRepository.updateProduct(existingProduct);
                return existingProduct;
            }

            return null;
        });
    }

    @Override
    public Promise<Void> deleteProduct(Long id) {
        return Blocking.get(() -> {
            Product product = productRepository.getProductById(id);

            if (product != null) {
                productRepository.deleteProduct(id);
            }

            return null;
        });
    }
}



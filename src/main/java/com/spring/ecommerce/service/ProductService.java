package com.spring.ecommerce.service;

import com.spring.ecommerce.entity.ProductEntity;
import com.spring.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductEntity getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    public void updateProduct(Long productId, ProductEntity updatedProduct) {
        ProductEntity product = getProductById(productId);
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}

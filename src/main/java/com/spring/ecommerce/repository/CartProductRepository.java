package com.spring.ecommerce.repository;

import com.spring.ecommerce.entity.CartEntity;
import com.spring.ecommerce.entity.CartProduct;
import com.spring.ecommerce.entity.CartProductId;
import com.spring.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, CartProductId> {

    void deleteByCartAndProduct(CartEntity cart, ProductEntity product);
}
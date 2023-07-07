package com.spring.ecommerce.repository;

import com.spring.ecommerce.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, String> {
    // No es necesario agregar métodos
}
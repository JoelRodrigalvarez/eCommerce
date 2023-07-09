package com.spring.ecommerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CART_PRODUCT")
public class CartProduct {
    @EmbeddedId
    @Getter
    @Setter
    private CartProductId id;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    @Getter @Setter
    private CartEntity cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @Getter @Setter
    private ProductEntity product;

    // Constructor, getters, setters, etc.
}
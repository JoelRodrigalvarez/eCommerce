package com.spring.ecommerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CartProductId implements Serializable {

    @Column(name = "cart_id")
    @Getter @Setter
    private String cartId;

    @Column(name = "product_id")
    @Getter @Setter
    private Long productId;

    // Constructor, getters, setters, etc.
}
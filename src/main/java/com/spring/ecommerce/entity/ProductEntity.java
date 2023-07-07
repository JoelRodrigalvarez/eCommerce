package com.spring.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private BigDecimal price; // Precio individual del producto

    @ManyToOne
    @Getter @Setter
    private CartEntity cart;
}
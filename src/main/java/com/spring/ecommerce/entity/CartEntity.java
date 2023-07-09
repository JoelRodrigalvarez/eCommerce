package com.spring.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CART")
public class CartEntity {
        @Id
        @Column(name = "cart_id")
        @Getter @Setter
        private String cartId;

        @Column(name = "total_price")
        @Getter @Setter
        private BigDecimal totalPrice; // Precio total del carrito

        @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
        @Getter @Setter
        private List<CartProduct> cartProducts = new ArrayList<>();

}

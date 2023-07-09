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
    @Getter @Setter
    private String cartId;

    @Getter @Setter
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @Column
    private List<ProductEntity> products;

    @Getter @Setter
    @Column
    private BigDecimal totalPrice; // Precio total del carrito

    public void addProduct(ProductEntity product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
        product.setCart(this);

        updateTotalPrice(); // Actualizar el precio total al agregar un producto
    }

    public void updateTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        if (products != null) {
            for (ProductEntity product : products) {
                totalPrice = totalPrice.add(product.getPrice()); // Sumar el precio de cada producto al precio total
            }

        }
    }

    public CartEntity(String cartId) {
        this.cartId = cartId;
    }
}

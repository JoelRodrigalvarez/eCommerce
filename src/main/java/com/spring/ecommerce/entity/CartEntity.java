package com.spring.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {

    @Id
    @Getter @Setter
    private String cartId;

    @Getter @Setter
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<ProductEntity> products;

    @Getter @Setter
    @DecimalMin(value = "0.0")
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

package com.spring.ecommerce.controller;

import com.spring.ecommerce.entity.CartEntity;
import com.spring.ecommerce.entity.ProductEntity;
import com.spring.ecommerce.service.CartService;
import com.spring.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<CartEntity> createCart() {
        CartEntity cart = cartService.createCart();
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartEntity> getCartById(@PathVariable String cartId) {
        CartEntity cart = cartService.getCartById(cartId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable String cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable String cartId, @PathVariable Long productId) {
        ProductEntity product = productService.getProductById(productId);
        cartService.addProductToCart(cartId, product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable String cartId, @PathVariable Long productId) {
        cartService.deleteProductFromCart(cartId, productId);
        return ResponseEntity.noContent().build();
    }
}

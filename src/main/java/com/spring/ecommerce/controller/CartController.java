package com.spring.ecommerce.controller;

import com.spring.ecommerce.entity.CartEntity;
import com.spring.ecommerce.entity.ProductEntity;
import com.spring.ecommerce.repository.CartRepository;
import com.spring.ecommerce.service.CartService;
import com.spring.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final CartRepository cartRepository;
    @Autowired
    public CartController(CartService cartService, ProductService productService, CartRepository cartRepository) {
        this.cartService = cartService;
        this.productService = productService;
        this.cartRepository = cartRepository;
    }

    @GetMapping("/all")
    public Iterable<CartEntity> getAllCarts() {
        List<CartEntity> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts).getBody();
    }
    @PostMapping("/new")
    public ResponseEntity<CartEntity> createCart(@RequestBody String cartId) {
        CartEntity createdCart = new CartEntity();
        createdCart.setCartId(cartId);
        cartService.createCart(createdCart);
        return ResponseEntity.ok(createdCart);
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
    public ResponseEntity<CartEntity> addProductToCart(@PathVariable String cartId, @PathVariable Long productId) {
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

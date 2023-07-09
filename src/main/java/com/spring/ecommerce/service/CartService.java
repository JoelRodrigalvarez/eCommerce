package com.spring.ecommerce.service;

import com.spring.ecommerce.entity.CartEntity;
import com.spring.ecommerce.entity.ProductEntity;
import com.spring.ecommerce.repository.CartRepository;
import com.spring.ecommerce.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CartService {
    private Map<String, CartEntity> carts = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    /*public CartEntity createCart() {
        String cartId = generateCartId();
        CartEntity cart = new CartEntity(cartId);
        carts.put(cartId, cart);
        scheduleCartDeletion(cartId); // Programar la eliminación del carrito después de 10 minutos
        return cart;
    }*/

    public CartEntity createCart(CartEntity cart) {
        cartRepository.save(cart);
        scheduleCartDeletion(cart.getCartId());
        return ResponseEntity.ok(cart).getBody();
    }

    public List<CartEntity> getAllCarts() {
        return cartRepository.findAll();
    }

    public CartEntity getCartById(String cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));
    }

    public void addProductToCart(String cartId, ProductEntity product) {
        CartEntity cart = getCartById(cartId);
        if (cart != null) {
            cart.addProduct(product);
            /*productRepository.save(product);
            cartRepository.save(cart);*/
        }
    }

    public void deleteCart(String cartId) {
        carts.remove(cartId);
    }

    private void scheduleCartDeletion(String cartId) {
        executorService.schedule(() -> {
            carts.remove(cartId);
        }, 10, TimeUnit.MINUTES);
    }

    public void deleteProductFromCart(String cartId, Long productId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        List<ProductEntity> products = cart.getProducts();
        products.removeIf(product -> product.getId().equals(productId));

        cartRepository.save(cart);
    }

}

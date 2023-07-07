package com.spring.ecommerce.service;

import com.spring.ecommerce.entity.CartEntity;
import com.spring.ecommerce.entity.ProductEntity;
import com.spring.ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CartService {
    private Map<String, CartEntity> carts = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartEntity createCart() {
        String cartId = generateCartId();
        CartEntity cart = new CartEntity(cartId);
        carts.put(cartId, cart);
        scheduleCartDeletion(cartId); // Programar la eliminación del carrito después de 10 minutos
        return cart;
    }

    public CartEntity getCartById(String cartId) {
        return carts.get(cartId);
    }

    public void addProductToCart(String cartId, ProductEntity product) {
        CartEntity cart = carts.get(cartId);
        if (cart != null) {
            cart.addProduct(product);
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
    private String generateCartId() {
        return UUID.randomUUID().toString();
    }

    public void deleteProductFromCart(String cartId, Long productId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        List<ProductEntity> products = cart.getProducts();
        products.removeIf(product -> product.getId().equals(productId));

        cartRepository.save(cart);
    }

}

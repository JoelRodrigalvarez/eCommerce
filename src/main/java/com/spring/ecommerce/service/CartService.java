package com.spring.ecommerce.service;

import com.spring.ecommerce.entity.CartEntity;
import com.spring.ecommerce.entity.CartProduct;
import com.spring.ecommerce.entity.CartProductId;
import com.spring.ecommerce.entity.ProductEntity;
import com.spring.ecommerce.repository.CartProductRepository;
import com.spring.ecommerce.repository.CartRepository;
import com.spring.ecommerce.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, CartProductRepository cartProductRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.productRepository = productRepository;
    }

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
            addProduct(cart, product);
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

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        if (cart != null && product != null) {
            // Elimina el producto del carrito
            cartProductRepository.deleteByCartAndProduct(cart, product);
        }
        cart.setTotalPrice(cart.getTotalPrice().subtract(product.getPrice())); // Restar al precio el producto eliminado

        cartRepository.save(cart);
    }


    public void addProduct(CartEntity cart, ProductEntity product) {

        CartProductId cartProductId = new CartProductId();
        cartProductId.setCartId(cart.getCartId());
        cartProductId.setProductId(product.getId());

        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(cartProductId);
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProductRepository.save(cartProduct);

        cart.getCartProducts().add(cartProduct);
        if (cart.getTotalPrice() == null){
            cart.setTotalPrice(BigDecimal.ZERO);
        }
        cart.setTotalPrice(cart.getTotalPrice().add(product.getPrice())); // Actualizar el precio total al agregar un producto
        cartRepository.save(cart);

    }
}

package com.Hussain.dreamshops.service.Cart;

import com.Hussain.dreamshops.exceptions.ResourceNotFoundException;
import com.Hussain.dreamshops.model.Cart;
import com.Hussain.dreamshops.model.CartItem;
import com.Hussain.dreamshops.model.Product;
import com.Hussain.dreamshops.repository.CartItemRepository;
import com.Hussain.dreamshops.repository.CartRepository;
import com.Hussain.dreamshops.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    // ================= ADD ITEM TO CART =================
    @Override
    public Cart addItemToCart(Long userId, Long productId, Integer quantity) {

        // 1️⃣ Get or create cart
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setTotalAmount(BigDecimal.ZERO);
            cart = cartRepository.save(cart);
        }

        // 2️⃣ Get product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // 3️⃣ Find existing cart item
        Optional<CartItem> existingItem =
                cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        CartItem cartItem;

        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }

        // 4️⃣ Calculate total price for this item
        cartItem.setTotalPrice(
                product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
        );

        cartItemRepository.save(cartItem);

        // 5️⃣ Update cart total
        cart.setTotalAmount(calculateTotal(cart));

        return cartRepository.save(cart);
    }

    // ================= GET CART =================
    @Override
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    // ================= CLEAR CART =================
    @Override
    public void clearCart(Long cartId) {
        Cart cart = getCart(cartId);
        cartItemRepository.deleteAllByCartId(cartId);
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    // ================= TOTAL PRICE =================
    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        return getCart(cartId).getTotalAmount();
    }

    // ================= INIT CART =================
    @Override
    public Long initializeNewCart() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.ZERO);
        return cartRepository.save(cart).getId();
    }

    // ================= CART BY USER =================
    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // ================= HELPER =================
    private BigDecimal calculateTotal(Cart cart) {
        return cartItemRepository.findByCartId(cart.getId())
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }
}

package com.popielarski.market.checkout.domain;

import com.popielarski.market.cart.Cart;
import com.popielarski.market.cart.CartDTO;
import com.popielarski.market.cart.CartMapper;
import com.popielarski.market.cart.CartRepository;
import com.popielarski.market.common.domain.Quantity;
import com.popielarski.market.common.exception.LogicValidationException;
import com.popielarski.market.common.utils.PriceUtils;
import com.popielarski.market.item.Item;
import com.popielarski.market.item.ItemFactory;
import com.popielarski.market.product.domain.Product;
import com.popielarski.market.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CheckoutFacade {

    private final CheckoutCacheRepository checkoutCacheRepository;

    private final ItemFactory itemFactory;

    private final CheckoutMapper checkoutMapper;

    private final CartMapper cartMapper;

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    public CheckoutReceiptDTO makePayment(Integer checkoutNumber, Integer amount) {
        log.debug("Making payment in checkout number {} with amount", checkoutNumber, amount);
        Cart cart = checkoutCacheRepository.findByCheckoutNumber(checkoutNumber)
                .orElseThrow(() -> new UnsupportedOperationException(String.format("Checkout number %d is not available, " +
                        "probably scanning has not been initialized or cart has been paid", checkoutNumber)));

        if (PriceUtils.isCovered(cart.getPrice().toValue(), amount)) {
            cart = findCartAndValidateBeforePayment(cart);
            cart.setPaid(Boolean.TRUE);

            deleteFromCacheAndSaveCart(checkoutNumber, cart);
            updateProductsQuantity(cart);

            CartDTO cartDTO = cartMapper.toDTO(cart);
            return checkoutMapper.toReceiptDTO(cart.getPrice().toValue(), amount, cartDTO);
        }

        throw new LogicValidationException(String.format("Amount doesn't cover total price, required amount: %d", cart.getPrice()
                .getValue()
                .intValue()));
    }

    public CheckoutScanDTO scanItem(Integer checkoutNumber, String barCode) {
        log.info("Scanning barCode {} in checkout number {}");
        Product product = productRepository.findByBarCode(barCode)
                .orElseThrow(() ->
                        new UnsupportedOperationException(String.format("Product with barCode %s does not exist", barCode)));

        Optional<Item> itemToScanOptional = checkoutCacheRepository.findItemByCheckoutNumberAndCode(checkoutNumber, barCode);

        Cart cart = checkoutCacheRepository.findById(Long.valueOf(checkoutNumber))
                .orElseThrow(() -> new UnsupportedOperationException(String.format("Checkout number %s is not available, " +
                        "probably scanning in this checkout has not been initialized", checkoutNumber)));

        if (itemToScanOptional.isPresent()) {
            updateQuantityAndSave(checkoutNumber, barCode, cart, itemToScanOptional.get());
        } else {
            addItemAndSave(checkoutNumber, product, cart);
        }

        CartDTO cartDTO = cartMapper.toDTO(cart);
        return checkoutMapper.toScanDTO(checkoutNumber, cartDTO);
    }


    public CheckoutStatusDTO initializeScanning(Integer checkoutNumber) {
        boolean isCheckoutAlreadyOccupied = checkoutCacheRepository.findByCheckoutNumber(checkoutNumber)
                .isPresent();
        log.info("Checkout {} already occupied: {}", checkoutNumber, isCheckoutAlreadyOccupied);

        if (isCheckoutAlreadyOccupied) {
            throw new UnsupportedOperationException(String.format("Checkout number %s is already occupied", checkoutNumber));
        } else {
            Cart cart = cartRepository.save(new Cart());
            checkoutCacheRepository.save(checkoutNumber, cart);

            log.info("Checkout is ready to scan items");
            return checkoutMapper.toStatusDTO(cart.getId(), checkoutNumber, CheckoutProcessStatus.WHILE_SCANNING);
        }
    }

    private void updateProductsQuantity(Cart cart) {
        Set<Item> items = cart.getItems().stream()
                .filter(this::productHasEligibleQuantity)
                .map(this::decreaseQuantityAndSaveProduct)
                .collect(Collectors.toSet());
        log.info("Products quantities have been decreased: {}", items);
        cart.setItems(items);
        cartRepository.save(cart);
    }

    private Item decreaseQuantityAndSaveProduct(Item item) {
        Product product = item.getProduct();
        product.decreaseQuantity(item.getQuantity());
        Product updatedProduct = productRepository.save(product);
        log.info("Updated product after decrease quantity: {}", updatedProduct);
        return item;
    }

    private boolean productHasEligibleQuantity(Item item) {
        if (item.couldBeBought()) {
            return true;
        }

        Product product = item.getProduct();
        throw new LogicValidationException(String.format("Insufficient quantity of product with barCode %s. " +
                        "Quantity of items: %d, quantity of products: %d", product.getBarCode(),
                item.getQuantity(),
                product.getQuantity()));
    }

    private void addItemAndSave(Integer checkoutNumber, Product product, Cart cart) {
        Item item = itemFactory.create(product);
        checkProductQuantity(product.getBarCode(), Quantity.ONE, item);

        cart.addItem(item);
        Cart persistedCart = cartRepository.save(cart);

        checkoutCacheRepository.save(checkoutNumber, persistedCart);
    }

    private void updateQuantityAndSave(Integer checkoutNumber, String barCode, Cart cart, Item item) {
        checkProductQuantity(barCode, Quantity.ONE, item);
        cart.increaseQuantityOfItem(Quantity.ONE, barCode);
        checkoutCacheRepository.save(checkoutNumber, cart);

        cart = cartRepository.save(cart);
        log.info("Persisted cart: {}", cart);
    }

    private void checkProductQuantity(String barCode, Integer quantityToIncrease, Item item) {
        Product product = productRepository.findByBarCode(barCode)
                .orElseThrow(() -> new LogicValidationException(String.format("Product with barCode %s does not exist",
                        barCode)));

        Integer finalQuantity = quantityToIncrease + item.getQuantity();

        if (finalQuantity > product.getQuantity()) {
            throw new LogicValidationException(String.format("There is no items anymore of product barCode: %s. " +
                            "You want to have %d items, but maximum quantity of product is %d",
                    barCode,
                    finalQuantity,
                    product.getQuantity()));
        }
    }

    private void deleteFromCacheAndSaveCart(Integer checkoutNumber, Cart cart) {
        checkoutCacheRepository.deleteById(Long.valueOf(checkoutNumber));

        cart = cartRepository.save(cart);
        log.info("Persisted cart: {}", cart);
    }

    private Cart findCartAndValidateBeforePayment(Cart cart) {
        Long id = cart.getId();
        cart = cartRepository.findById(id)
                .orElseThrow(() -> new LogicValidationException(String.format("Cart with id %d does not exist", id)));

        if (cart.isPaid()) {
            throw new LogicValidationException(String.format("Cart with id %d is already paid.", id));
        }
        return cart;
    }
}

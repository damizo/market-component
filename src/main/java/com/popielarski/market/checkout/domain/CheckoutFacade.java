package com.popielarski.market.checkout.domain;

import com.popielarski.market.cart.Cart;
import com.popielarski.market.cart.CartDTO;
import com.popielarski.market.cart.CartMapper;
import com.popielarski.market.cart.CartRepository;
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
                        "probably scanning in this checkout has not been initialized", checkoutNumber)));

        if (PriceUtils.isCovered(cart.getPrice().toValue(), amount)) {
            cart.setPaid(Boolean.TRUE);
            deleteFromCacheAndSaveCart(checkoutNumber, cart);

            CartDTO cartDTO = cartMapper.toDTO(cart);
            return checkoutMapper.toReceiptDTO(cart.getPrice().toValue(), amount, cartDTO);
        }

        throw new LogicValidationException(String.format("Amount doesn't cover total price, required amount: %d", cart.getFinalPrice()
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
            updateQuantityAndSave(checkoutNumber, barCode, cart);
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

    private void addItemAndSave(Integer checkoutNumber, Product product, Cart cart) {
        Item item = itemFactory.create(product);
        cart.addItem(item);
        Cart persistedCart = cartRepository.save(cart);
        checkoutCacheRepository.save(checkoutNumber, persistedCart);
    }

    private void updateQuantityAndSave(Integer checkoutNumber, String barCode, Cart cart) {
        cart.increaseQuantityOfItem(Item.DEFAULT_QUANTITY, barCode);
        checkoutCacheRepository.save(checkoutNumber, cart);

        cart = cartRepository.save(cart);
        log.info("Persisted cart: {}", cart);
    }

    private void deleteFromCacheAndSaveCart(Integer checkoutNumber, Cart cart) {
        checkoutCacheRepository.deleteById(Long.valueOf(checkoutNumber));

        cart = cartRepository.save(cart);
        log.info("Persisted cart: {}", cart);
    }
}

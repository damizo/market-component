package com.popielarski.market.checkout.domain;

import com.popielarski.market.cart.Cart;
import com.popielarski.market.cart.CartDTO;
import com.popielarski.market.cart.CartMapper;
import com.popielarski.market.cart.CartRepository;
import com.popielarski.market.common.exception.LogicValidationException;
import com.popielarski.market.common.PriceUtils;
import com.popielarski.market.item.ItemFactory;
import com.popielarski.market.item.domain.Item;
import com.popielarski.market.item.domain.ItemMapper;
import com.popielarski.market.item.domain.ItemRepository;
import com.popielarski.market.product.Product;
import com.popielarski.market.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutFacade {

    private final CheckoutCacheRepository checkoutCacheRepository;

    private final ItemRepository itemRepository;

    private final ItemFactory itemFactory;

    private final ItemMapper itemMapper;

    private final CheckoutMapper checkoutMapper;

    private final CartMapper cartMapper;

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    public CheckoutReceiptDTO makePayment(Integer checkoutNumber, Integer amount) {
        log.debug("Making payment in checkout number {} with amount", checkoutNumber, amount);
        Cart cart = checkoutCacheRepository.findByCheckoutNumber(checkoutNumber)
                .orElseThrow(() -> new UnsupportedOperationException(String.format("Checkout number %s is not available, " +
                        "probably scanning in this checkout has not been initialized", checkoutNumber)));

        if (PriceUtils.isCovered(cart.getPrice(), amount)) {
            checkoutCacheRepository.deleteById(Long.valueOf(checkoutNumber));
            cartRepository.save(cart);

            CartDTO cartDTO = cartMapper.toDTO(cart);
            return checkoutMapper.toReceiptDTO(cart.getPrice(), checkoutNumber, amount, cartDTO);
        }

        throw new LogicValidationException(String.format("Amount doesn't cover total price, required amount: %d", cart.getFinalPrice()));
    }


    @Transactional
    public CheckoutScanDTO scanItem(Integer checkoutNumber, String barCode) {
        log.debug("Scanning barCode {} in checkout number {}");
        Product product = productRepository.findByBarCode(barCode)
                .orElseThrow(() ->
                        new UnsupportedOperationException(String.format("Item with barCode %s does not exist", barCode)));

        Optional<Item> itemToScanOptional = checkoutCacheRepository.findItemByCheckoutNumberAndCode(checkoutNumber, barCode);

        Cart cart = checkoutCacheRepository.findById(Long.valueOf(checkoutNumber))
                .orElseThrow(() -> new UnsupportedOperationException(String.format("Checkout number %s is not available, " +
                        "probably scanning in this checkout has not been initialized", checkoutNumber)));

        if (itemToScanOptional.isPresent()) {
            cart.increaseQuantityOfItem(Item.DEFAULT_QUANTITY, barCode);
            checkoutCacheRepository.save(checkoutNumber, cart);
        } else {
            Item item = itemFactory.create(product);
            cart.addItem(item);
            Cart persistedCart = cartRepository.save(cart);
            checkoutCacheRepository.save(checkoutNumber, persistedCart);
        }

        product.decreaseQuantity(Item.DEFAULT_QUANTITY);
        productRepository.save(product);

        CartDTO cartDTO = cartMapper.toDTO(cart);
        return checkoutMapper.toScanDTO(checkoutNumber, cartDTO);
    }

    public CheckoutStatusDTO initializeScanning(Integer checkoutNumber) {
        boolean isCheckoutAlreadyOccupied = checkoutCacheRepository.findByCheckoutNumber(checkoutNumber)
                .isPresent();
        log.debug("Checkout {} already occupied: {}", checkoutNumber, isCheckoutAlreadyOccupied);

        if (isCheckoutAlreadyOccupied) {
            throw new UnsupportedOperationException(String.format("Checkout number %s is already occupied", checkoutNumber));
        } else {
            Cart cart = cartRepository.save(new Cart());
            checkoutCacheRepository.save(checkoutNumber, cart);

            log.debug("Checkout is ready to scan items");
            return checkoutMapper.toStatusDTO(cart.getId(), checkoutNumber, CheckoutStatus.SCANNING);
        }
    }

}

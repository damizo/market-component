package com.popielarski.market.checkout.domain;

import com.popielarski.market.cart.CartDTO;
import com.popielarski.market.common.domain.Calculator;
import com.popielarski.market.common.domain.PriceDTO;


public class CheckoutMapper {

    public CheckoutStatusDTO toStatusDTO(Long id, Integer checkoutNumber, CheckoutProcessStatus status) {
        return new CheckoutStatusDTO.Builder()
                .cartId(id)
                .checkoutNumber(checkoutNumber)
                .status(status)
                .build();
    }

    public CheckoutReceiptDTO toReceiptDTO(PriceDTO price, Integer amount, CartDTO cartDTO) {
        return CheckoutReceiptDTO.builder()
                .status(CheckoutProcessStatus.PAID)
                .finalPrice(price)
                .items(cartDTO.getItems())
                .change(Calculator.subtract(amount, price))
                .appliedDiscount(cartDTO.getDiscount())
                .build();
    }

    public CheckoutScanDTO toScanDTO(Integer checkoutNumber, CartDTO cartDTO) {
        return CheckoutScanDTO.builder()
                .checkoutNumber(checkoutNumber)
                .items(cartDTO.getItems())
                .totalPrice(cartDTO.getTotalPrice())
                .build();
    }
}

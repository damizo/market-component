package com.popielarski.market.checkout.domain;

import com.popielarski.market.cart.CartDTO;


public class CheckoutMapper {

    public CheckoutStatusDTO toStatusDTO(Long id, Integer checkoutNumber, CheckoutStatus status) {
        return new CheckoutStatusDTO.Builder()
                .cartId(id)
                .checkoutNumber(checkoutNumber)
                .status(status)
                .build();
    }

    public CheckoutReceiptDTO toReceiptDTO(Long price, Integer checkoutNumber, Integer amount, CartDTO cartDTO) {
        return CheckoutReceiptDTO.builder()
                .status(CheckoutProcessStatus.PAID)
                .finalPrice(price)
                .items(cartDTO.getItems())
                .change(amount - price)
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

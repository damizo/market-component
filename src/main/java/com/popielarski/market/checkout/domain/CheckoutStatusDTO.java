package com.popielarski.market.checkout.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutStatusDTO {

    protected Long cartId;
    protected Integer checkoutNumber;
    protected CheckoutProcessStatus status;

    public static class Builder {

        private Long cartId;
        private Integer checkoutNumber;
        private CheckoutProcessStatus status;

        Builder checkoutNumber(Integer checkoutNumber) {
            this.checkoutNumber = checkoutNumber;
            return this;
        }

        Builder status(CheckoutProcessStatus status) {
            this.status = status;
            return this;
        }

        Builder cartId(Long cartId) {
            this.cartId = cartId;
            return this;
        }

        CheckoutStatusDTO build() {
            return new CheckoutStatusDTO(cartId, checkoutNumber, status);
        }
    }

}

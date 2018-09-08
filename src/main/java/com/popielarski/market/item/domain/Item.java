package com.popielarski.market.item.domain;


import com.popielarski.market.cart.Cart;
import com.popielarski.market.common.Calculator;
import com.popielarski.market.common.domain.BaseEntity;
import com.popielarski.market.product.Price;
import com.popielarski.market.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Item extends BaseEntity {

    public static final Integer DEFAULT_QUANTITY = 1;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    public Item() {

    }

    public void decreaseQuantity(Integer quantity) {
        this.quantity -= quantity;
        Integer currentQuantity = this.product.getQuantity();
        this.product.setQuantity(currentQuantity += quantity);
    }

    public Item increaseQuantity(Integer quantity) {
        this.quantity += quantity;
        Integer currentQuantity = this.product.getQuantity();
        this.product.setQuantity(currentQuantity -= quantity);
        return this;
    }

    public Price getTotalPrice() {
        return Calculator.multiple(quantity, product.getPrice());
    }


}

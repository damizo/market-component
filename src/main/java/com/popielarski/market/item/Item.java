package com.popielarski.market.item;


import com.popielarski.market.cart.Cart;
import com.popielarski.market.common.domain.BaseEntity;
import com.popielarski.market.common.domain.Calculator;
import com.popielarski.market.product.domain.Price;
import com.popielarski.market.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Item extends BaseEntity {

    public static final Integer DEFAULT_QUANTITY = 1;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    public Item() {
        //JPA requires default constructor
    }

    public Item increaseQuantity(Integer quantity) {
        this.quantity += quantity;
        return this;
    }

    public Price getTotalPrice() {
        return Calculator.multiple(quantity, product.getPrice());
    }

    public boolean couldBeBought() {
        return quantity <= this.product.getQuantity();
    }
}

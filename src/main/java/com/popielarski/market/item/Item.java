package com.popielarski.market.item;


import com.popielarski.market.cart.Cart;
import com.popielarski.market.common.domain.BaseEntity;
import com.popielarski.market.common.domain.Calculator;
import com.popielarski.market.product.domain.Price;
import com.popielarski.market.product.domain.Product;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    public Item() {
        //JPA requires default constructor
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

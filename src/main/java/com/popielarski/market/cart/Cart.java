package com.popielarski.market.cart;

import com.google.common.collect.Sets;
import com.popielarski.market.common.domain.BaseEntity;
import com.popielarski.market.common.domain.Calculator;
import com.popielarski.market.discount.domain.DiscountType;
import com.popielarski.market.item.Item;
import com.popielarski.market.product.domain.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Cart extends BaseEntity {

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<Item> items = Sets.newHashSet();

    @Column(name = "IS_DISCOUNT_APPLIED")
    private Boolean discountApplied;

    @Column(name = "APPLIED_DISCOUNT")
    @Enumerated(EnumType.STRING)
    private DiscountType discount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRICE_ID")
    private Price price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FINAL_PRICE_ID")
    private Price finalPrice;

    @Column(name = "PAID")
    private Boolean paid;

    public Cart() {
        discountApplied = Boolean.FALSE;
        paid = Boolean.FALSE;
    }

    private boolean isMoreThanOne(Item item) {
        return item.getQuantity() > 1;
    }

    public Price getTotalPriceOfItems() {
        return this.items.stream()
                .map(Item::getTotalPrice)
                .reduce(Calculator::add)
                .get();
    }

    public void increaseQuantityOfItem(Integer quantity, String barCode) {
        Optional<Item> itemOptional = this.items.stream()
                .map(item -> item.increaseQuantity(quantity))
                .findAny();

        if (itemOptional.isPresent()) {
            this.items.add(itemOptional.get());
        } else {
            throw new UnsupportedOperationException(String.format("Item with barCode %s does not exist in cart", barCode));
        }
    }

    public void addItem(Item item) {
        this.items.add(item);
        item.setCart(this);
        this.price = item.getTotalPrice();
    }

    public Price getPrice() {
        return this.getFinalPrice() != null ?
                this.getFinalPrice() : this.getTotalPriceOfItems();
    }

    public boolean isPaid() {
        return this.paid;
    }

    public void applyDiscount(DiscountType discountType) {
        this.discountApplied = Boolean.TRUE;
        this.discount = discountType;
    }

    public boolean isDiscountApplied() {
        return this.discountApplied;
    }

}

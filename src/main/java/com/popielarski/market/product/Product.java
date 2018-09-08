package com.popielarski.market.product;

import com.popielarski.market.common.domain.BaseEntity;
import com.popielarski.market.discount.boughttogether.BoughtTogetherDiscount;
import com.popielarski.market.item.domain.Item;
import com.popielarski.market.item.domain.MultiItemsDiscount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "BAR_CODE")
    private String barCode;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRICE_ID")
    private Price price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MULTI_PRICE_DISCOUNT_ID")
    private MultiItemsDiscount multiItemsDiscount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOUGHT_TOGETHER_DISCOUNT_ID")
    private BoughtTogetherDiscount boughtTogetherDiscount;

    public Product() {

    }

    public void decreaseQuantity(Integer quantity) {
        this.quantity -= quantity;
    }

    public void increaseQuantity(Integer quantity) {
        this.quantity += quantity;
    }

    public boolean hasBoughtTogetherDiscount() {
        return boughtTogetherDiscount != null;
    }

    public boolean hasMultiItemDiscount() {
        return multiItemsDiscount != null;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}

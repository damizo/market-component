package com.popielarski.market.product.domain;

import com.popielarski.market.common.domain.BaseEntity;
import com.popielarski.market.discount.domain.boughttogether.BoughtTogetherDiscount;
import com.popielarski.market.discount.domain.multiitems.MultiItemsDiscount;
import com.popielarski.market.item.Item;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
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
        //JPA requires default constructor
    }

    public void decreaseQuantity(Integer quantity) {
        Integer productQuantity = this.quantity - quantity;
        this.quantity = productQuantity;
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

}

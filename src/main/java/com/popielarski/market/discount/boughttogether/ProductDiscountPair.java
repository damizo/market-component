package com.popielarski.market.discount.boughttogether;

import com.popielarski.market.common.domain.BaseEntity;
import com.popielarski.market.product.domain.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDiscountPair extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "FIRST_PRODUCT_ID")
    private Product firstProduct;

    @OneToOne
    @JoinColumn(name = "SECOND_PRODUCT_ID")
    private Product secondProduct;

    @ManyToOne
    @JoinColumn(name="BOUGHT_TOGETHER_DISCOUNT_ID")
    private BoughtTogetherDiscount boughtTogetherDiscount;

}

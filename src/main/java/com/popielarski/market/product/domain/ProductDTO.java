package com.popielarski.market.product.domain;

import com.popielarski.market.common.domain.BaseDTO;
import com.popielarski.market.common.domain.PriceDTO;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductDTO extends BaseDTO {
    private String name;
    private PriceDTO price;
    private Integer quantity;
    private String barCode;

    @Builder
    public ProductDTO(Long id, String name, PriceDTO price, Integer quantity, String barCode) {
        super(id);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.barCode = barCode;
    }

}

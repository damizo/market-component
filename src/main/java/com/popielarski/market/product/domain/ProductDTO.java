package com.popielarski.market.product.domain;

import com.popielarski.market.common.domain.BaseDTO;
import com.popielarski.market.common.domain.PriceDTO;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO)) return false;
        if (!super.equals(o)) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(barCode, that.barCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, price, quantity, barCode);
    }
}
